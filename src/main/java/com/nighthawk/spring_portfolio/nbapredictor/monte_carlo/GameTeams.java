package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import java.util.List;

public class GameTeams {
    private List<Player> teamA;
    private List<Player> teamB;

    // Constructors
    public GameTeams() {}

    public GameTeams(List<Player> teamA, List<Player> teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
    }

    // Getters and setters
    public List<Player> getTeamA() {
        return teamA;
    }

    public void setTeamA(List<Player> teamA) {
        this.teamA = teamA;
    }

    public List<Player> getTeamB() {
        return teamB;
    }

    public void setTeamB(List<Player> teamB) {
        this.teamB = teamB;
    }
}