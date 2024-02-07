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
        // Assuming you have a variable to hold the number of simulations
        int simulations = 1000; // You can adjust the number of simulations as needed

        MonteCarloSimulator simulator = new MonteCarloSimulator();
        return simulator.simulate(teams, simulations);
    }

    // This endpoint could be used to receive past game statistics from the frontend
    @PostMapping("/past-stats")
    public void receivePastStats(@RequestBody List<PlayerStats> pastStats) {
        // Here you can process the received past game statistics as needed
        // For example, you might store them in a database for future reference
    }
}
