package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class GameTeamsParser {

    private final ObjectMapper objectMapper;

    public GameTeamsParser() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Parses a JSON string into a GameTeams object.
     *
     * @param jsonData The JSON string representing team A and team B with players and stats.
     * @return A GameTeams object populated with data from the JSON string.
     * @throws IOException If parsing the JSON string fails.
     */
    public GameTeams parse(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, GameTeams.class);
    }
}
