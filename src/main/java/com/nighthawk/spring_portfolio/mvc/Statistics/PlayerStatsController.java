package com.nighthawk.spring_portfolio.mvc.Statistics;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nba")
public class PlayerStatsController {
    private JSONObject body;
    private HttpStatus status;
    String lastRun = null;

    @GetMapping("/players")
    public ResponseEntity<JSONObject> getNBAPlayers() {
        // Call NBA API once a day, set body and status properties
        String today = new Date().toString().substring(0, 10);
        if (lastRun == null || !today.equals(lastRun)) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://www.balldontlie.io/api/v1/players"))
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                        HttpResponse.BodyHandlers.ofString());

                // JSONParser extracts text body and parses to JSONObject
                this.body = (JSONObject) new JSONParser().parse(response.body());
                this.status = HttpStatus.OK;
                this.lastRun = today;
            } catch (Exception e) {
                HashMap<String, String> status = new HashMap<>();
                status.put("status", "NBA API failure: " + e);

                // Setup object for error
                this.body = (JSONObject) status;
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
                this.lastRun = null;
            }
        }

        // Return JSONObject in RESTful style
        return new ResponseEntity<>(body, status);
    }
}
