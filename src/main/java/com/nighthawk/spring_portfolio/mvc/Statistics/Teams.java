package com.nighthawk.spring_portfolio.mvc.Statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
@RestController
@RequestMapping("/teams")
@CrossOrigin(origins = {"http://localhost:4200", "https://jishnus420.github.io", "http://127.0.0.1:4000", "https://pitsco.github.io"})
public class Teams {

    private static final String API_URL = "https://api.balldontlie.io/v1/teams";
    private static final String API_KEY = "f8073004-09ea-475c-840b-380354e78ae5";

    @GetMapping("/{teamId}")
    public String getTeamData(@PathVariable int teamId) {
        String url = API_URL + "/" + teamId;

        // Create HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create HttpRequest with headers
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", API_KEY)
                .GET()
                .build();

        try {
            // Send the request and handle the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error fetching team data: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
