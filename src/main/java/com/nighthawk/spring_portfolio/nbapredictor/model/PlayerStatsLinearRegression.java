package com.nighthawk.spring_portfolio.nbapredictor.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerStatsLinearRegression {

    private LinearRegressionModel pointsRegressionModel;
    private LinearRegressionModel reboundsRegressionModel;
    private LinearRegressionModel assistsRegressionModel;

    public PlayerStatsLinearRegression() {
        this.pointsRegressionModel = new LinearRegressionModel();
        this.reboundsRegressionModel = new LinearRegressionModel();
        this.assistsRegressionModel = new LinearRegressionModel();
    }

    public Map<String, Object> predictNextStats(Player player) {
        List<PlayerStats> stats = player.getStats();
        double totalPoints = stats.stream().mapToDouble(PlayerStats::getPoints).sum();
        double totalRebounds = stats.stream().mapToDouble(PlayerStats::getRebounds).sum();
        double totalAssists = stats.stream().mapToDouble(PlayerStats::getAssists).sum();

        // Train models
        pointsRegressionModel.train(List.of(totalPoints), List.of(totalPoints)); // Dummy labels, not used
        reboundsRegressionModel.train(List.of(totalRebounds), List.of(totalRebounds)); // Dummy labels, not used
        assistsRegressionModel.train(List.of(totalAssists), List.of(totalAssists)); // Dummy labels, not used

        // Get coefficients and intercepts
        List<Double> pointsCoefficients = pointsRegressionModel.getCoefficients();
        double pointsIntercept = pointsRegressionModel.getIntercept();
        List<Double> reboundsCoefficients = reboundsRegressionModel.getCoefficients();
        double reboundsIntercept = reboundsRegressionModel.getIntercept();
        List<Double> assistsCoefficients = assistsRegressionModel.getCoefficients();
        double assistsIntercept = assistsRegressionModel.getIntercept();

        // Predictions for next values
        double nextPointsPrediction = pointsCoefficients.get(0) * totalPoints + pointsIntercept;
        double nextReboundsPrediction = reboundsCoefficients.get(0) * totalRebounds + reboundsIntercept;
        double nextAssistsPrediction = assistsCoefficients.get(0) * totalAssists + assistsIntercept;

        // Construct response
        Map<String, Object> response = new HashMap<>();
        response.put("points_coefficients", pointsCoefficients);
        response.put("points_intercept", pointsIntercept);
        response.put("next_points_prediction", nextPointsPrediction);
        response.put("rebounds_coefficients", reboundsCoefficients);
        response.put("rebounds_intercept", reboundsIntercept);
        response.put("next_rebounds_prediction", nextReboundsPrediction);
        response.put("assists_coefficients", assistsCoefficients);
        response.put("assists_intercept", assistsIntercept);
        response.put("next_assists_prediction", nextAssistsPrediction);

        return response;
    }
}