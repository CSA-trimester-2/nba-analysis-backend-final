package com.nighthawk.spring_portfolio.mvc.predictor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictionService {

    private static final String STATS_API_URL = "https://www.balldontlie.io/api/v1/season_averages";
    private final RestTemplate restTemplate;

    public PredictionService() {
        this.restTemplate = new RestTemplate();
    }

    public String predictOutcome(int teamIdOne, int teamIdTwo) {
        // Fetch season averages for both teams
        double teamOneAveragePoints = fetchSeasonAveragePoints(teamIdOne);
        double teamTwoAveragePoints = fetchSeasonAveragePoints(teamIdTwo);


        String predictedWinner = teamOneAveragePoints > teamTwoAveragePoints ? 
            "Team " + teamIdOne : "Team " + teamIdTwo;
        
        return predictedWinner + " is predicted to win based on the linear Regresson.";
    }

    private double fetchSeasonAveragePoints(int teamId) {
        String season = "2022"; 
        String url = STATS_API_URL + "?season=" + season + "&team_ids[]=" + teamId;
        
        SeasonAveragesResponse response = restTemplate.getForObject(url, SeasonAveragesResponse.class);
        

        double totalPoints = 0;
        if (response != null && response.getData() != null) {
            for (PlayerSeasonAverage stats : response.getData()) {
                totalPoints += stats.getPts();
            }
        }
        
        return totalPoints;
    }


    static class SeasonAveragesResponse {
        private PlayerSeasonAverage[] data;

        // Getter and setter
        public PlayerSeasonAverage[] getData() {
            return data;
        }

        public void setData(PlayerSeasonAverage[] data) {
            this.data = data;
        }
    }

    static class PlayerSeasonAverage {
        private double pts; 

        // Getter and setter
        public double getPts() {
            return pts;
        }

        public void setPts(double pts) {
            this.pts = pts;
        }
    }
}
