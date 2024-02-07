package com.nighthawk.spring_portfolio.nbapredictor.model;

import java.util.ArrayList;
import java.util.List;

public class LinearRegressionModel extends RegressionModel {

    @Override
    public void train(List<Double> x_train, List<Double> y_train) {
        // Simple implementation of the least squares method for linear regression
        // Note: This is a simplistic implementation and assumes a single feature for simplicity
        double xMean = x_train.stream().mapToDouble(val -> val).average().orElse(0.0);
        double yMean = y_train.stream().mapToDouble(val -> val).average().orElse(0.0);

        double numerator = 0.0;
        double denominator = 0.0;
        for (int i = 0; i < x_train.size(); i++) {
            numerator += (x_train.get(i) - xMean) * (y_train.get(i) - yMean);
            denominator += (x_train.get(i) - xMean) * (x_train.get(i) - xMean);
        }

        // Calculating coefficients
        double beta = numerator / denominator;
        double alpha = yMean - (beta * xMean);

        // Storing coefficients and intercept
        this.coefficients = new ArrayList<>();
        this.coefficients.add(beta);
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
