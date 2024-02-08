package com.nighthawk.spring_portfolio.nbapredictor.model;

import java.io.IOException; // Import IOException
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinearController {

    @PostMapping("/api/stats/predict-next-stats")
    public Map<String, Object> predictNextStats(@RequestBody String jsonData) {
        System.out.println("Received JSON data: " + jsonData); // Print JSON data for debugging
        PlayerParser parser = new PlayerParser();
        Player player;
        try {
            player = parser.parse(jsonData);
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            // Handle the IOException appropriately
            // For example, you can return an error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to parse player data from JSON.");
            return errorResponse;
        }

        PlayerStatsService statsService = new PlayerStatsService();
        return statsService.predictNextStats(player);
    }
}
