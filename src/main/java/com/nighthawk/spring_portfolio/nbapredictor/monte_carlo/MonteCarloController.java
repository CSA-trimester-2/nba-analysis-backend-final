package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import com.nighthawk.spring_portfolio.nbapredictor.monte_carlo.MonteCarloSimulator;
import com.nighthawk.spring_portfolio.nbapredictor.monte_carlo.PlayerStats;
import com.nighthawk.spring_portfolio.nbapredictor.monte_carlo.TeamStats;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MonteCarloController {

    @PostMapping("/predict")
    public String predict(@RequestBody TeamStats teams) {
        // variable for # of simulations
        int simulations = 1000; 

        MonteCarloSimulator simulator = new MonteCarloSimulator();
        return simulator.simulate(teams, simulations);
    }

    // past game stats from frontend
    @PostMapping("/past-stats")
    public void receivePastStats(@RequestBody List<PlayerStats> pastStats) {
        System.out.println("Received past game statistics:");
        for (PlayerStats playerStats : pastStats) {
            System.out.println("Player: " + playerStats.getName());
            System.out.println("Stats: " + playerStats.getStats());
            System.out.println("Standard Deviations: " + playerStats.getStdDeviations());
            System.out.println("---------------------------------------------");
        }
    }
}
