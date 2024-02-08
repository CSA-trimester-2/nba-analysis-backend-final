package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MonteCarloSimulator {

    private final Random random = new Random();

    public Map<String, Double[]> simulateFantasyPoints(GameTeams teamsData) {
        double standardDeviation = calculateStandardDeviation(teamsData);
        double meanFantasyPoints = calculateMeanFantasyPoints(teamsData);
    
        int numberOfSimulations = 20; // Change to 20 simulations
        List<Double> projectedFantasyPointsA = new ArrayList<>();
        List<Double> projectedFantasyPointsB = new ArrayList<>();
    
        // Perform Monte Carlo simulations
        for (int i = 0; i < numberOfSimulations; i++) {
            double[] fantasyPoints = simulateFantasyPoints(meanFantasyPoints, standardDeviation);
            projectedFantasyPointsA.add(fantasyPoints[0]);
            projectedFantasyPointsB.add(fantasyPoints[1]);
        }
    
        // Calculate average projections for each team
        double averageProjectionA = calculateAverage(projectedFantasyPointsA);
        double averageProjectionB = calculateAverage(projectedFantasyPointsB);
    
        // Prepare response map
        Map<String, Double[]> response = new HashMap<>();
        response.put("projectedFantasyPointsTeamA", new Double[] { averageProjectionA });
        response.put("projectedFantasyPointsTeamB", new Double[] { averageProjectionB });
    
        return response;
    }
    

    private double calculateStandardDeviation(GameTeams teamsData) {
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

    private double calculateFantasyPoints(Player player) {
        PlayerStats stats = player.getStats();
        return stats.getPoints() * 1.0 + stats.getRebounds() * 1.2 + stats.getAssists() * 1.5;
    }

    private double[] simulateFantasyPoints(double mean, double standardDeviation) {
        double[] fantasyPoints = new double[2];
        fantasyPoints[0] = random.nextGaussian() * standardDeviation + mean; // Projection for team A
        fantasyPoints[1] = random.nextGaussian() * standardDeviation + mean; // Projection for team B
        return fantasyPoints;
    }

    private double calculateMeanFantasyPoints(GameTeams teamsData) {
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

    private double calculateAverage(List<Double> values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }
}
