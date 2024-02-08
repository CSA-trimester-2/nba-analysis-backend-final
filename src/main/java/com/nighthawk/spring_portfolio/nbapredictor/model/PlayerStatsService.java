package com.nighthawk.spring_portfolio.nbapredictor.model;

import com.nighthawk.spring_portfolio.nbapredictor.model.Player;
import com.nighthawk.spring_portfolio.nbapredictor.model.PlayerStats;
import com.nighthawk.spring_portfolio.nbapredictor.model.LinearRegressionModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerStatsService {

    public Map<String, Object> predictNextStats(Player player) {
        List<PlayerStats> stats = player.getStats();
        double totalPoints = stats.stream().mapToDouble(PlayerStats::getPoints).sum();
        double totalRebounds = stats.stream().mapToDouble(PlayerStats::getRebounds).sum();
        double totalAssists = stats.stream().mapToDouble(PlayerStats::getAssists).sum();

        // Training models
        LinearRegressionModel pointsRegressionModel = new LinearRegressionModel();
        pointsRegressionModel.train(List.of(totalPoints), List.of(totalPoints)); // Dummy labels, not used

        LinearRegressionModel reboundsRegressionModel = new LinearRegressionModel();
        reboundsRegressionModel.train(List.of(totalRebounds), List.of(totalRebounds)); // Dummy labels, not used

        LinearRegressionModel assistsRegressionModel = new LinearRegressionModel();
        assistsRegressionModel.train(List.of(totalAssists), List.of(totalAssists)); // Dummy labels, not used

        // Predictions
        double nextPointsPrediction = pointsRegressionModel.predict(List.of(totalPoints)).get(0);
        double nextReboundsPrediction = reboundsRegressionModel.predict(List.of(totalRebounds)).get(0);
        double nextAssistsPrediction = assistsRegressionModel.predict(List.of(totalAssists)).get(0);

        // Construct response
        Map<String, Object> response = new HashMap<>();
        response.put("next_points_prediction", nextPointsPrediction);
        response.put("next_rebounds_prediction", nextReboundsPrediction);
        response.put("next_assists_prediction", nextAssistsPrediction);

        return response;
    }
}
