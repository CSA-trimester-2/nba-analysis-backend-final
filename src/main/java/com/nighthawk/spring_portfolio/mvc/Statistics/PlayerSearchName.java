package com.nighthawk.spring_portfolio.mvc.Statistics;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = {"http://localhost:4200","https://jishnus420.github.io", "http://127.0.0.1:4000", "https://pitsco.github.io", })
public class PlayerSearchName {

    private final String API_URL = "https://www.balldontlie.io/api/v1/players";

    @GetMapping
    public String getPlayersByName(@RequestParam String search) {
        // Construct the API URL with the search parameter
        String apiUrlWithSearch = API_URL + "?search=" + search;

        // Use RestTemplate to make a GET request to the API
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(apiUrlWithSearch, String.class);

        // Return the result
        return result;
    }
}


