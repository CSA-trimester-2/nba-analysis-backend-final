package com.nighthawk.spring_portfolio.mvc.Statistics;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://jishnus420.github.io", "http://127.0.0.1:4000", "https://pitsco.github.io"})
public class PlayerStats {

    private final String API_URL = "https://www.balldontlie.io/api/v1/stats";

    @GetMapping("/nba/stats/{year}/{playerId}")
    public String getNBAStats(
            @PathVariable int year,
            @PathVariable int playerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int perPage,
            @RequestParam(required = false) String[] dates,
            @RequestParam(required = false) String[] seasons,
            @RequestParam(required = false) int[] gameIds,
            @RequestParam(defaultValue = "false") boolean postseason,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String gameDate // New parameter for specific game date
    ) {
        // Construct the URL with year and player ID
        StringBuilder apiUrl = new StringBuilder(API_URL + "?page=" + page + "&per_page=" + perPage + "&postseason=" + postseason
                + "&seasons[]=" + year + "&player_ids[]=" + playerId);

        // Add optional parameters
        if (dates != null) {
            for (String date : dates) {
                apiUrl.append("&dates[]=").append(date);
            }
        }

        if (seasons != null) {
            for (String season : seasons) {
                apiUrl.append("&seasons[]=").append(season);
            }
        }

        if (gameIds != null) {
            for (int gameId : gameIds) {
                apiUrl.append("&game_ids[]=").append(gameId);
            }
        }

        if (startDate != null) {
            apiUrl.append("&start_date=").append(startDate);
        }

        if (endDate != null) {
            apiUrl.append("&end_date=").append(endDate);
        }

        // Add specific game date parameter
        if (gameDate != null) {
            apiUrl.append("&dates[]=").append(gameDate);
        }

        // Make the HTTP request
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(apiUrl.toString(), String.class);

        return result;
    }
}
