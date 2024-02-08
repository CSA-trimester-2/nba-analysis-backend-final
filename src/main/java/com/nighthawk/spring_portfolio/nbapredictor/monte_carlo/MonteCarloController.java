package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import com.nighthawk.spring_portfolio.nbapredictor.monte_carlo.MonteCarloSimulator;
import com.nighthawk.spring_portfolio.nbapredictor.monte_carlo.PlayerStats;
import com.nighthawk.spring_portfolio.nbapredictor.monte_carlo.TeamStats;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MonteCarloController {

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

    public static double calculateStandardDeviation(Map<String, List<Player>> teamsData) {
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

    public static double calculateFantasyPoints(Player player) {
        // Calculate fantasy points based on player stats (e.g., points, rebounds, assists)
        return player.getStats().getPoints() * 1.0 + player.getStats().getRebounds() * 1.2 + player.getStats().getAssists() * 1.5;
    }

    public static double simulateFantasyPoints(double mean, double standardDeviation) {
        Random random = new Random();
        // Generate a random number from a normal distribution with given mean and standard deviation
        return random.nextGaussian() * standardDeviation + mean;
    }

    public static double calculateMeanFantasyPoints(Map<String, List<Player>> teamsData) {
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

    public static void main(String[] args) {
        // Dummy data provided as a JSON string
        String dummyData = "{\"teamA\":[{\"name\":\"Stephen Domingo\",\"stats\":{\"points\":6,\"rebounds\":5,\"assists\":4}},{\"name\":\"Nikola Jokic\",\"stats\":{\"points\":0,\"rebounds\":0,\"assists\":0}},{\"name\":\"LeBron James\",\"stats\":{\"points\":0,\"rebounds\":0,\"assists\":0}}],\"teamB\":[{\"name\":\"Joel Embiid\",\"stats\":{\"points\":0,\"rebounds\":0,\"assists\":0}},{\"name\":\"Luka Doncic\",\"stats\":{\"points\":0,\"rebounds\":0,\"assists\":0}},{\"name\":\"Anthony Edwards\",\"stats\":{\"points\":0,\"rebounds\":0,\"assists\":0}}]}";

        // Convert JSON string to Java object
        Gson gson = new Gson();
        Map<String, List<Player>> teamsData = gson.fromJson(dummyData, Map.class);

        // Calculate the standard deviation of fantasy points
        double standardDeviation = calculateStandardDeviation(teamsData);

        // Assuming mean fantasy points is the sum of fantasy points divided by total players
        double meanFantasyPoints = calculateMeanFantasyPoints(teamsData);

        // Perform Monte Carlo simulation to project fantasy points
        int numberOfSimulations = 10000; // Adjust this based on your needs
        double[] projectedFantasyPoints = new double[numberOfSimulations];
        for (int i = 0; i < numberOfSimulations; i++) {
            projectedFantasyPoints[i] = simulateFantasyPoints(meanFantasyPoints, standardDeviation);
            System.out.println("Projected Fantasy Points for Simulation " + (i+1) + ": " + projectedFantasyPoints[i]);
        }

        // You can analyze the projectedFantasyPoints array to get insights about the fantasy points distribution
        // For example, you can calculate percentiles, average, maximum, minimum, etc.
    }

}
