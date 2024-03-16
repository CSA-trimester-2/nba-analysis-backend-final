package com.nighthawk.spring_portfolio.mvc.Statistics;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders; // Import HttpHeaders

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = {"http://127.0.0.1:4000"})
public class PlayerSearchName {

    private final String API_URL = "https://api.balldontlie.io/v1/players";
    private final String API_KEY = "f8073004-09ea-475c-840b-380354e78ae5"; // Replace with your actual API key

    @GetMapping
    public String getPlayersByName(@RequestParam String search) {
        // Construct the API URL with the search parameter
        String apiUrlWithSearch = API_URL + "?search=" + search;

        // Set up headers with the API key
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", API_KEY);

        // Use RestTemplate to make a GET request to the API with headers
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(apiUrlWithSearch, String.class);

        // Return the result
        return result;
    }
}
