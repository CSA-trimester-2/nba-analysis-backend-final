package com.nighthawk.spring_portfolio.nbapredictor.model;

import java.util.ArrayList;
import java.util.List;

public class LinearRegressionModel extends RegressionModel {

    @Override
    public void train(List<Double> x_train, List<Double> y_train) {
        // Simple implementation of the least squares method for linear regression
        // Note: This is a simplistic implementation and assumes a single feature for simplicity
        if (x_train.isEmpty() || y_train.isEmpty() || x_train.size() != y_train.size()) {
            throw new IllegalArgumentException("Invalid training data.");
        }

        double xMean = x_train.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double yMean = y_train.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        double numerator = 0.0;
        double denominator = 0.0;
        for (int i = 0; i < x_train.size(); i++) {
            numerator += (x_train.get(i) - xMean) * (y_train.get(i) - yMean);
            denominator += Math.pow(x_train.get(i) - xMean, 2);
        }

        if (denominator == 0.0) {
            throw new IllegalArgumentException("Denominator is zero.");
        }

        // Calculating coefficients
        double beta = numerator / denominator;
        double alpha = yMean - (beta * xMean);

        // Storing coefficients and intercept
        this.coefficients = List.of(beta);
        this.intercept = alpha;
    }

    @Override
    public List<Double> predict(List<Double> x_test) {
        List<Double> predictions = new ArrayList<>();
        for (double x : x_test) {
            double y_pred = this.coefficients.get(0) * x + this.intercept;
            predictions.add(y_pred);
        }
        return predictions;
    }

    @Override
    public double evaluate(List<Double> x_test, List<Double> y_test) {
        List<Double> predictions = predict(x_test);
        return meanSquaredError(y_test, predictions);
    }
}