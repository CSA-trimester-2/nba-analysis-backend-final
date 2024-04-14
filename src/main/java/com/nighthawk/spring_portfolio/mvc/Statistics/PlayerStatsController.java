package com.nighthawk.spring_portfolio.mvc.Statistics;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PlayerStatsController {

    private final String API_KEY = "f8073004-09ea-475c-840b-380354e78ae5";
    private final int PER_PAGE = 25;

    @GetMapping("/stats/{playerId}")
    public ResponseEntity<String> getPlayerStats(@PathVariable int playerId) {
        return getPlayerStatsWithCursor(playerId, 0);
    }

    private ResponseEntity<String> getPlayerStatsWithCursor(int playerId, int cursor) {
        String apiUrl = "https://api.balldontlie.io/v1/stats?player_ids[]=" + playerId + "&per_page=" + PER_PAGE + "&cursor=" + cursor;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", API_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new ResponseEntity<>(response.body(), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "NBA API failure: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
