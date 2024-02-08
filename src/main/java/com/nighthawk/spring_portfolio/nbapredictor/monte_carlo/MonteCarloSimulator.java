package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MonteCarloSimulator {

    private final Random random = new Random();

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