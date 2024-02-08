package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MonteCarloSimulator {

    static class Player {
        private String name;
        private Stats stats;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Stats getStats() {
            return stats;
        }

        public void setStats(Stats stats) {
            this.stats = stats;
        }
    }

    static class Stats {
        private int points;
        private int rebounds;
        private int assists;

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getRebounds() {
            return rebounds;
        }

        public void setRebounds(int rebounds) {
            this.rebounds = rebounds;
        }

        public int getAssists() {
            return assists;
        }

        public void setAssists(int assists) {
            this.assists = assists;
        }
    }

    public Map<String, Double[]> simulateFantasyPoints(String jsonData) {
        // Parse JSON data into teamsData
        Map<String, List<Player>> teamsData = parseJsonData(jsonData);

        // Calculate the standard deviation of fantasy points
        double standardDeviation = calculateStandardDeviation(teamsData);

        // Assuming mean fantasy points is the sum of fantasy points divided by total players
        double meanFantasyPoints = calculateMeanFantasyPoints(teamsData);

        // Perform Monte Carlo simulation to project fantasy points
        int numberOfSimulations = 10000; // Adjust this based on your needs
        Double[] projectedFantasyPoints = new Double[numberOfSimulations];
        for (int i = 0; i < numberOfSimulations; i++) {
            projectedFantasyPoints[i] = simulateFantasyPoints(meanFantasyPoints, standardDeviation);
        }

        // Prepare response map
        Map<String, Double[]> response = new HashMap<>();
        response.put("projectedFantasyPoints", projectedFantasyPoints);

        return response;
    }

    // Parses JSON string into Map<String, List<Player>>
    private Map<String, List<Player>> parseJsonData(String jsonData) {
        Map<String, List<Player>> teamsData = new HashMap<>();
        List<Player> teamA = new ArrayList<>();
        List<Player> teamB = new ArrayList<>();
        String[] parts = jsonData.split("\"team[AB]\":\\[");

        // Extract player data for team A
        String[] playersData = parts[1].split("\\},\\{");
        for (String playerData : playersData) {
            Player player = new Player();
            String[] playerParts = playerData.split("\"name\":\"");
            player.setName(playerParts[1].split("\",")[0]);
            String[] statsParts = playerData.split("\"points\":|,\"rebounds\":|,\"assists\":|\\}");
            Stats stats = new Stats();
            stats.setPoints(Integer.parseInt(statsParts[1]));
            stats.setRebounds(Integer.parseInt(statsParts[2]));
            stats.setAssists(Integer.parseInt(statsParts[3]));
            player.setStats(stats);
            teamA.add(player);
        }

        // Extract player data for team B
        playersData = parts[2].split("\\},\\{");
        for (String playerData : playersData) {
            Player player = new Player();
            String[] playerParts = playerData.split("\"name\":\"");
            player.setName(playerParts[1].split("\",")[0]);
            String[] statsParts = playerData.split("\"points\":|,\"rebounds\":|,\"assists\":|\\}");
            Stats stats = new Stats();
            stats.setPoints(Integer.parseInt(statsParts[1]));
            stats.setRebounds(Integer.parseInt(statsParts[2]));
            stats.setAssists(Integer.parseInt(statsParts[3]));
            player.setStats(stats);
            teamB.add(player);
        }

        // Add teams to the map
        teamsData.put("teamA", teamA);
        teamsData.put("teamB", teamB);

        return teamsData;
    }

    public double calculateStandardDeviation(Map<String, List<Player>> teamsData) {
        double sum = 0;
        int totalPlayers = 0;

        // Calculate the total sum of fantasy points
        for (List<Player> team : teamsData.values()) {
            for (Player player : team) {
                sum += calculateFantasyPoints(player);
                totalPlayers++;
            }
        }

        // Calculate the mean of fantasy points
        double mean = sum / totalPlayers;

        // Calculate the sum of squared differences from the mean
        double squaredDifferenceSum = 0;
        for (List<Player> team : teamsData.values()) {
            for (Player player : team) {
                double difference = calculateFantasyPoints(player) - mean;
                squaredDifferenceSum += difference * difference;
            }
        }

        // Calculate the variance
        double variance = squaredDifferenceSum / totalPlayers;

        // Calculate the standard deviation
        return Math.sqrt(variance);
    }

    public double calculateFantasyPoints(Player player) {
        // Calculate fantasy points based on player stats (e.g., points, rebounds, assists)
        return player.getStats().getPoints() * 1.0 + player.getStats().getRebounds() * 1.2 + player.getStats().getAssists() * 1.5;
    }

    public double simulateFantasyPoints(double mean, double standardDeviation) {
        Random random = new Random();
        // Generate a random number from a normal distribution with given mean and standard deviation
        return random.nextGaussian() * standardDeviation + mean;
    }

    public double calculateMeanFantasyPoints(Map<String, List<Player>> teamsData) {
        double sum = 0;
        int totalPlayers = 0;

        // Calculate the total sum of fantasy points
        for (List<Player> team : teamsData.values()) {
            for (Player player : team) {
                sum += calculateFantasyPoints(player);
                totalPlayers++;
            }
        }

        // Calculate the mean of fantasy points
        return sum / totalPlayers;
    }
}
