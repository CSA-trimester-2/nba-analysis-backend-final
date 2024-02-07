package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import java.util.Map;

public class PlayerStats {

    private String name;
    private Map<String, Double> stats; // Average statistics
    private Map<String, Double> stdDeviations; // Standard deviations for the stats

    // Default constructor
    public PlayerStats() {}

    // Constructor with parameters
    public PlayerStats(String name, Map<String, Double> stats, Map<String, Double> stdDeviations) {
        this.name = name;
        this.stats = stats;
        this.stdDeviations = stdDeviations;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Double> getStats() {
        return stats;
    }

    public void setStats(Map<String, Double> stats) {
        this.stats = stats;
    }

    public Map<String, Double> getStdDeviations() {
        return stdDeviations;
    }

    public void setStdDeviations(Map<String, Double> stdDeviations) {
        this.stdDeviations = stdDeviations;
    }
}