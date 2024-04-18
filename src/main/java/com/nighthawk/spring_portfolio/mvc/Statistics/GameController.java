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
        // Construct the API URL with the provided parameters
        String url = API_URL + "?player_ids[]=" + playerId + "&seasons[]=" + season;

        try {
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

            // Return the response from the API
            return ResponseEntity.status(response.statusCode()).body(response.body());
        } catch (Exception e) {
            // Return an error response in case of exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching player games: " + e.getMessage());
        }
    }
}
