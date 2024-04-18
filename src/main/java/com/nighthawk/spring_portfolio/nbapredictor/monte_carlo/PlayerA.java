package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

public class PlayerA {
    private int id;
    private String name;
    private PlayerStats stats;

    // Constructor, getters, and setters
    public PlayerA(int id, String name, PlayerStats stats) {
        this.id = id;
        this.name = name;
        this.stats = stats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void setStats(PlayerStats stats) {
        this.stats = stats;
    }
}
