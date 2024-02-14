package com.nighthawk.spring_portfolio.mvc.Statistics;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/player")
@CrossOrigin(origins = {"http://localhost:4200","https://jishnus420.github.io", "http://127.0.0.1:4000", "https://pitsco.github.io", })
public class PlayerSeasonAverage {

    private final String apiUrl = "https://www.balldontlie.io/api/v1/season_averages";

    @GetMapping("/{playerId}")
    public ResponseEntity<String> getPlayerStats(@PathVariable Long playerId) {
        String url = apiUrl + "?player_ids[]=" + playerId;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok(result);
    }
}
