package com.nighthawk.spring_portfolio.nbapredictor.model;

import com.nighthawk.spring_portfolio.nbapredictor.model.Player;
import com.nighthawk.spring_portfolio.nbapredictor.model.PlayerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LinearController {

    @Autowired
    private PlayerStatsService playerStatsService;

    @PostMapping("/api/stats/predict-next-stats")
    public ResponseEntity<Map<String, Object>> predictNextStats(@RequestBody Player player) {
        Map<String, Object> prediction = playerStatsService.predictNextStats(player);
        return new ResponseEntity<>(prediction, HttpStatus.OK);
    }
}
