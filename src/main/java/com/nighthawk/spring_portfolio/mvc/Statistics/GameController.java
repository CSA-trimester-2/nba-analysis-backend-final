package com.nighthawk.spring_portfolio.mvc.Statistics;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = {"http://localhost:4200", "https://jishnus420.github.io", "http://127.0.0.1:4000", "https://pitsco.github.io"})
public class GameController {
    private static final String API_URL = "https://api.balldontlie.io/v1/stats";
    private static final String API_KEY = "f8073004-09ea-475c-840b-380354e78ae5";

    @GetMapping("/{playerId}/{season}")
    @ResponseBody
    public ResponseEntity<String> getPlayerGames(
            @PathVariable("playerId") int playerId,
            @PathVariable("season") int season
    ) {
        // Start with cursor as null to fetch the first page of results
        Integer cursor = null;
        StringBuilder resultBuilder = new StringBuilder();

        try {
            // Continue fetching data until there are no more pages
            do {
                // Construct the API URL with the provided parameters and cursor
                String url = buildUrl(playerId, season, cursor);

                // Create HttpClient
                HttpClient client = HttpClient.newHttpClient();

                // Create HttpRequest with headers
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Authorization", API_KEY)
                        .GET()
                        .build();

                // Send the request and handle the response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Append the response body to the result builder
                resultBuilder.append(response.body());

                // Extract next_cursor from meta key in the response
                String meta = response.headers().firstValue("meta").orElse(null);
                if (meta != null) {
                    cursor = extractNextCursor(meta);
                } else {
                    cursor = null; // No more pages
                }

            } while (cursor != null);

            // Return the concatenated response from all pages
            return ResponseEntity.status(HttpStatus.OK).body(resultBuilder.toString());

        } catch (Exception e) {
            // Return an error response in case of exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching player games: " + e.getMessage());
        }
    }

    // Helper method to build the API URL with parameters and cursor
    private String buildUrl(int playerId, int season, Integer cursor) {
        String url = API_URL + "?player_ids[]=" + playerId + "&seasons[]=" + season;
        url += "&per_page=100"; // Set per_page to 100
        if (cursor != null) {
            url += "&page=" + (cursor / 100 + 1); // Calculate the page number based on cursor
        }
        return url;
    }

    // Helper method to extract next_cursor from meta key in the response
    private int extractNextCursor(String meta) {
        String[] parts = meta.split(",");
        for (String part : parts) {
            if (part.contains("next_cursor")) {
                String[] keyValue = part.split(":");
                return Integer.parseInt(keyValue[1].trim());
            }
        }
        return -1; // If next_cursor not found
    }
}
