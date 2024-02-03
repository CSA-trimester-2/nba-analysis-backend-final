package com.nighthawk.spring_portfolio.mvc.Statistics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Team {
    public static void main(String[] args) {
        try {
            String url = "https://www.balldontlie.io/api/v1/teams";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // Parse and handle the JSON response
                // You may use a JSON library like Gson for parsing
                // For simplicity, we'll just print the raw content
                System.out.println(content.toString());
            } else {
                System.out.println("Error: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

