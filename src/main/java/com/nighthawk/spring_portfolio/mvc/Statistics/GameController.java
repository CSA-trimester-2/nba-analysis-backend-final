package com.nighthawk.spring_portfolio.mvc.Statistics;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = {"http://127.0.0.1:4000"})
public class GameController {

    private static final String API_URL = "https://api.balldontlie.io/v1/stats";
    private static final String API_KEY = "f8073004-09ea-475c-840b-380354e78ae5";

    @GetMapping("/{playerId}/{season}")
    @ResponseBody
    public ResponseEntity<String> getPlayerGames(
            @PathVariable("playerId") int playerId,
            @PathVariable("season") int season
    ) {
        // Construct the API URL with the provided parameters
        String url = API_URL + "?player_ids[]=" + playerId + "&seasons[]=" + season;

        // Create headers with API key
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", API_KEY);

        // Make a GET request to the API
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Return the response from the API
        return response;
    }
}
