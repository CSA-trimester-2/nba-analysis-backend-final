package com.nighthawk.spring_portfolio.nbapredictor.model;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class NbaScheduleService {

    
    private final String apiKey = "9184d53847d84a4f8cf3eeaf5957daa5";

    public List<NbaGame> getNbaSchedule(String season) {
        String url = "https://api.sportsdata.io/v3/nba/scores/json/SchedulesBasic/" + season + "?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        NbaGame[] games = restTemplate.getForObject(url, NbaGame[].class);
        return Arrays.asList(games);
    }


    
}
