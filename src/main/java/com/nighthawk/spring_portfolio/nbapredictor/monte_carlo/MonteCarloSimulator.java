package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonteCarloSimulator {

    private final Random random = new Random();

    public double calculateStandardDeviation(GameTeams teamsData) {
        double sum = 0;
        int totalPlayers = 0;

        List<Player> allPlayers = new ArrayList<>(teamsData.getTeamA());
        allPlayers.addAll(teamsData.getTeamB());

        for (Player player : allPlayers) {
            sum += calculateFantasyPoints(player);
            totalPlayers++;
        }

        double mean = sum / totalPlayers;
        double squaredDifferenceSum = 0;

        for (Player player : allPlayers) {
            double difference = calculateFantasyPoints(player) - mean;
            squaredDifferenceSum += difference * difference;
        }

        double variance = squaredDifferenceSum / totalPlayers;
        return Math.sqrt(variance);
    }

    public double calculateFantasyPoints(Player player) {
        PlayerStats stats = player.getStats();
        return stats.getPoints() * 1.0 + stats.getRebounds() * 1.2 + stats.getAssists() * 1.5;
    }

    public Map<String, Double[]> simulateFantasyPoints(GameTeams teamsData) {
        double standardDeviation = calculateStandardDeviation(teamsData);
        double meanFantasyPoints = calculateMeanFantasyPoints(teamsData);

        int numberOfSimulations = 10000;
        Double[] projectedFantasyPoints = new Double[numberOfSimulations];
        for (int i = 0; i < numberOfSimulations; i++) {
            projectedFantasyPoints[i] = simulateFantasyPoints(meanFantasyPoints, standardDeviation);
        }

        Map<String, Double[]> response = new HashMap<>();
        response.put("projectedFantasyPoints", projectedFantasyPoints);

        return response;
    }

    public double calculateMeanFantasyPoints(GameTeams teamsData) {
        double sum = 0;
        int totalPlayers = 0;

        List<Player> allPlayers = new ArrayList<>(teamsData.getTeamA());
        allPlayers.addAll(teamsData.getTeamB());

        for (Player player : allPlayers) {
            sum += calculateFantasyPoints(player);
            totalPlayers++;
        }

        return sum / totalPlayers;
    }

    private double simulateFantasyPoints(double mean, double standardDeviation) {
        return random.nextGaussian() * standardDeviation + mean;
    }
}
