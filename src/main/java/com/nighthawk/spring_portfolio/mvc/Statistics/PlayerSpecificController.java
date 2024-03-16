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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nba")
@CrossOrigin(origins = {"http://localhost:4200", "https://jishnus420.github.io", "http://127.0.0.1:4000", "https://pitsco.github.io"})
public class PlayerSpecificController {
    private JSONObject body;
    private HttpStatus status;
    private String lastRun = null;
    private final String API_KEY = "f8073004-09ea-475c-840b-380354e78ae5";

    @GetMapping("/players")
    public ResponseEntity<JSONObject> getNBAPlayers() {
        // Call NBA API once a day, set body and status properties
        String today = new Date().toString().substring(0, 10);
        if (lastRun == null || !today.equals(lastRun)) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.balldontlie.io/v1/players"))
                        .header("Authorization", API_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                        HttpResponse.BodyHandlers.ofString());

                // Parse response body to JSONObject
                JSONParser parser = new JSONParser();
                JSONObject responseBody = (JSONObject) parser.parse(response.body());

                this.body = responseBody;
                this.status = HttpStatus.OK;
                this.lastRun = today;
            } catch (Exception e) {
                HashMap<String, String> status = new HashMap<>();
                status.put("status", "NBA API failure: " + e.getMessage());

                // Setup object for error
                this.body = new JSONObject(status);
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
                this.lastRun = null;
            }
        }
        return new ResponseEntity<>(body, status);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<String> getNBAPlayerById(@PathVariable Long id) {
        // Construct the API URL with the player ID
        String apiUrl = "https://api.balldontlie.io/v1/players/" + id;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", API_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());

            // Return the player information as a string
            return new ResponseEntity<>(response.body(), HttpStatus.OK);
        } catch (Exception e) {
            HashMap<String, String> errorStatus = new HashMap<>();
            errorStatus.put("status", "NBA API failure: " + e.getMessage());

            // Setup object for error
            JSONObject errorBody = new JSONObject(errorStatus);
            return new ResponseEntity<>(errorBody.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
