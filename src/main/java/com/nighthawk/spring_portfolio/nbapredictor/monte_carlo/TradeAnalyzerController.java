package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.nighthawk.spring_portfolio.nbapredictor.monte_carlo.PlayerA;
import com.nighthawk.spring_portfolio.nbapredictor.monte_carlo.PlayerStatsA;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/trades")
public class TradeAnalyzerController {

    // Dummy list of players
    List<PlayerA> players = Arrays.asList(
        new PlayerA(1, "LeBron James", new PlayerStatsA(25, 7, 7)),
        new PlayerA(2, "Kevin Durant", new PlayerStatsA(27, 5, 7)),
        // Continue adding players as needed
    );

    @PostMapping("/evaluate")
    public ResponseEntity<Map<String, Object>> evaluateTrade(@RequestBody Map<String, Integer> playerIds) {
        PlayerA player1 = players.stream()
                                 .filter(p -> p.getId() == playerIds.get("player1"))
                                 .findFirst()
                                 .orElse(null);
        PlayerA player2 = players.stream()
                                 .filter(p -> p.getId() == playerIds.get("player2"))
                                 .findFirst()
                                 .orElse(null);

        if (player1 == null || player2 == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Players not found"));
        }

        double score1 = calculateScore(player1.getStats());
        double score2 = calculateScore(player2.getStats());

        String recommendation = score1 > score2 ? player1.getName() : player2.getName();

        Map<String, Object> response = new HashMap<>();
        response.put("score1", score1);
        response.put("score2", score2);
        response.put("recommendation", recommendation);

        return ResponseEntity.ok(response);
    }

    private double calculateScore(PlayerStatsA stats) {
        return stats.getPoints() + stats.getAssists() * 2 + stats.getRebounds() * 1.5;
    }
}
