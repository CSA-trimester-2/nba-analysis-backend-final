package com.nighthawk.spring_portfolio.nbapredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.sql.Timestamp;

@Entity
public class NbaGame {
    @Id
    @JsonProperty("GameID")
    private Long gameId;

    @JsonProperty("Season")
    private Integer season;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Day")
    private Timestamp day;

    @JsonProperty("AwayTeam")
    private String awayTeam;

    @JsonProperty("HomeTeam")
    private String homeTeam;

    @JsonProperty("AwayTeamScore")
    private Integer awayTeamScore;

    @JsonProperty("HomeTeamScore")
    private Integer homeTeamScore;

    // Getters and Setters
    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }
    public Integer getSeason() { return season; }
    public void setSeason(Integer season) { this.season = season; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getDay() { return day; }
    public void setDay(Timestamp day) { this.day = day; }
    public String getAwayTeam() { return awayTeam; }
    public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }
    public String getHomeTeam() { return homeTeam; }
    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }
    public Integer getAwayTeamScore() { return awayTeamScore; }
    public void setAwayTeamScore(Integer awayTeamScore) { this.awayTeamScore = awayTeamScore; }
    public Integer getHomeTeamScore() { return homeTeamScore; }
    public void setHomeTeamScore(Integer homeTeamScore) { this.homeTeamScore = homeTeamScore; }
}
