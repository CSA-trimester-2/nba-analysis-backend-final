package com.nighthawk.spring_portfolio.nbapredictor.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerParser {

    private final ObjectMapper objectMapper;

    public PlayerParser() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Parses a JSON string into a Player object.
     *
     * @param jsonData The JSON string representing a player with stats.
     * @return A Player object populated with data from the JSON string.
     * @throws IOException If parsing the JSON string fails.
     */
    public Player parse(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, Player.class);
    }
}