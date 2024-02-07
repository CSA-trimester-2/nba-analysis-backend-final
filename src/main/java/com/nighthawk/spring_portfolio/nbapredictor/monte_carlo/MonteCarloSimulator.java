package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MonteCarloSimulator {

    private final Random random = new Random();

    public String simulate(TeamStats teams, int simulations) {
        int teamAWins = 0;
        int teamBWins = 0;

        for (int i = 0; i < simulations; i++) {
            double teamAScore = simulateTeamPerformance(teams.getTeamA());
            double teamBScore = simulateTeamPerformance(teams.getTeamB());

            if (teamAScore > teamBScore) {
                teamAWins++;
            } else {
                teamBWins++;
            }
        }

        return getResultMessage(teamAWins, teamBWins, simulations);
    }

    private double simulateTeamPerformance(List<PlayerStats> team) {
        double teamScore = 0;
        for (PlayerStats player : team) {
            teamScore += simulatePlayerPerformance(player);
        }
        return teamScore;
    }

    private double simulatePlayerPerformance(PlayerStats player) {
        double playerScore = 0;
        Map<String, Double> stats = player.getStats();
        Map<String, Double> stdDeviations = player.getStdDeviations(); // Assuming this method exists and provides standard deviations

        for (String statKey : stats.keySet()) {
            double average = stats.get(statKey);
            double stdDeviation = stdDeviations.get(statKey);
            // Simulate performance using a normal distribution around the player's average stat
            double simulatedPerformance = random.nextGaussian() * stdDeviation + average;
            playerScore += Math.max(0, simulatedPerformance); // Ensure no negative performances
        }

        return playerScore;
    }

    private String getResultMessage(int teamAWins, int teamBWins, int simulations) {
        String message = "After " + simulations + " simulations: \n" +
                         "Team A wins: " + teamAWins + " times\n" +
                         "Team B wins: " + teamBWins + " times\n";
        if (teamAWins > teamBWins) {
            message += "Team A is more likely to win";
        } else if (teamBWins > teamAWins) {
            message += "Team B is more likely to win";
        } else {
            message += "It's a tie!";
        }
        return message;
    }
}