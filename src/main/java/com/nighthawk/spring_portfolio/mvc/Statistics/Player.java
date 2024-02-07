package com.nighthawk.spring_portfolio.mvc.Statistics;
// PlayerService.java
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Player {
    private static final String API_URL = "https://www.balldontlie.io/api/v1/players";

    public Player[] getPlayers() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(API_URL, Player[].class);
    }
}
