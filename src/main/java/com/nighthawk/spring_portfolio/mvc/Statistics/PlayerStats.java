package com.nighthawk.spring_portfolio.mvc.Statistics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PlayerStats {
    public static void main(String[] args) {
        try {
            String baseUrl = "https://www.balldontlie.io/api/v1/teams";
            String searchQuery = "davis"; // Replace with your search query

            // Encode the search query to handle special characters
            String encodedSearchQuery = URLEncoder.encode(searchQuery, "UTF-8");

            // Append the search parameter to the URL
            String url = baseUrl + "?search=" + encodedSearchQuery;

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
