package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MonteCarloController {

    @PostMapping("/simulate")
    public Map<String, Double[]> simulateFantasyPoints(@RequestBody String jsonData) {
        MonteCarloSimulator simulator = new MonteCarloSimulator();
        GameTeamsParser parser = new GameTeamsParser();
        
        try {
            // Parse the JSON data into a GameTeams object
            GameTeams gameTeams = parser.parse(jsonData);
            
            // Pass the GameTeams object to the simulator
            return simulator.simulateFantasyPoints(gameTeams);
        } catch (IOException e) {
            e.printStackTrace(); // Handle parsing exception appropriately
            return new HashMap<>(); // Return empty map or error response
        }
    }
}