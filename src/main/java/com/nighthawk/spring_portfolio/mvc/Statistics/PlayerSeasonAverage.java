package com.nighthawk.spring_portfolio.mvc.Statistics;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nba")
@CrossOrigin(origins = {"http://127.0.0.1:4000"})
public class PlayerSeasonAverage {
    private final String API_URL = "https://api.balldontlie.io/v1/season_averages";
    private final String API_KEY = "f8073004-09ea-475c-840b-380354e78ae5";
    private final int SEASON = 2023; // Hardcoded season value

    @GetMapping("/playerseason")
    public ResponseEntity<String> searchNBAPlayerBySeasonAndIDs(@RequestParam("player_ids") Long playerID) {
        try {
            // Construct the API URL with the correct query parameters
            String apiUrl = API_URL + "?season=" + SEASON + "&player_ids[]=" + playerID;

            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", API_KEY)
                .GET()
                .build();

            // Send the HTTP request and handle the response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());

            // Return the response body with OK status
            return new ResponseEntity<>(response.body(), HttpStatus.OK);
        } catch (Exception e) {
            // Handle errors and return appropriate response
            HashMap<String, String> errorStatus = new HashMap<>();
            errorStatus.put("status", "NBA API failure: " + e.getMessage());

            // Setup object for error
            JSONObject errorBody = new JSONObject(errorStatus);
            return new ResponseEntity<>(errorBody.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
