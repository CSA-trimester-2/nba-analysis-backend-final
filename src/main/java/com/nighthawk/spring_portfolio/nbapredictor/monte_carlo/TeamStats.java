package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TeamStats {

    private List<PlayerStats> teamA;
    private List<PlayerStats> teamB;

    // Default constructor
    public TeamStats() {}

    // Constructor with parameters
    public TeamStats(List<PlayerStats> teamA, List<PlayerStats> teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
    }

    // Getters and Setters
    @JsonProperty("teamA")
    public List<PlayerStats> getTeamA() {
        return teamA;
    }

    public void setTeamA(List<PlayerStats> teamA) {
        this.teamA = teamA;
    }

    @JsonProperty("teamB")
    public List<PlayerStats> getTeamB() {
        return teamB;
    }

    public void setTeamB(List<PlayerStats> teamB) {
        this.teamB = teamB;
    }

    // You might also include methods to perform calculations or transformations on the stats
}
