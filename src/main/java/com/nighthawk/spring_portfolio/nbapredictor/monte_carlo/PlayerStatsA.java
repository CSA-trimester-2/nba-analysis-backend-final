package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

public class PlayerStatsA {
    private double points;
    private double assists;
    private double rebounds;

    // Constructor, getters, and setters
    public PlayerStatsA(double points, double assists, double rebounds) {
        this.points = points;
        this.assists = assists;
        this.rebounds = rebounds;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getAssists() {
        return assists;
    }

    public void setAssists(double assists) {
        this.assists = assists;
    }

    public double getRebounds() {
        return rebounds;
    }

    public void setRebounds(double rebounds) {
        this.rebounds = rebounds;
    }
}
