package com.nighthawk.spring_portfolio.nbapredictor.model;

public class PlayerStats {
    private int gameNumber;
    private double points;
    private double rebounds;
    private double assists;

    public PlayerStats(int gameNumber, double points, double rebounds, double assists) {
        this.gameNumber = gameNumber;
        this.points = points;
        this.rebounds = rebounds;
        this.assists = assists;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getRebounds() {
        return rebounds;
    }

    public void setRebounds(double rebounds) {
        this.rebounds = rebounds;
    }

    public double getAssists() {
        return assists;
    }

    public void setAssists(double assists) {
        this.assists = assists;
    }
}