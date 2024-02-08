package com.nighthawk.spring_portfolio.nbapredictor.model;

import java.util.List;

public class Player {
    private String name;
    private List<PlayerStats> stats;

    public Player(String name, List<PlayerStats> stats) {
        this.name = name;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerStats> getStats() {
        return stats;
    }

    public void setStats(List<PlayerStats> stats) {
        this.stats = stats;
    }
}