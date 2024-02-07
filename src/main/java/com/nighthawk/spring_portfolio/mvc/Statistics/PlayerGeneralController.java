package com.nighthawk.spring_portfolio.mvc.Statistics;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nba")
@CrossOrigin(origins = {"http://localhost:4200", "https://jishnus420.github.io", "http://127.0.0.1:4000", "https://pitsco.github.io"})
public class PlayerGeneralController {
    private List<JSONObject> playersData;
    private HttpStatus status;
    String lastRun = null;

    @GetMapping("/playersall")
    public ResponseEntity<List<JSONObject>> getNBAPlayers() {
        // Call NBA API once a day, set playersData and status properties
        String today = new Date().toString().substring(0, 10);
        if (lastRun == null || !today.equals(lastRun)) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://www.balldontlie.io/api/v1/players"))
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                        HttpResponse.BodyHandlers.ofString());

                // Parse the JSON response
                JSONObject jsonResponse = (JSONObject) new JSONParser().parse(response.body());
                JSONArray playerDataArray = (JSONArray) jsonResponse.get("data");

                // Set playersData as the list of player data
                this.playersData = new ArrayList<>(playerDataArray);
                this.status = HttpStatus.OK;
                this.lastRun = today;
            } catch (Exception e) {
                // Handle exception
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
                this.lastRun = null;
            }
        }

        return new ResponseEntity<>(playersData, status);
    }

    // Other methods...
}
