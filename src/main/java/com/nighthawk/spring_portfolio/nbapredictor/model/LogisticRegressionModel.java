package com.nighthawk.spring_portfolio.nbapredictor.model;

import java.util.ArrayList;
import java.util.List;

public class LogisticRegressionModel extends RegressionModel {

    private final double learningRate;
    private final int maxIterations;

    public LogisticRegressionModel(double learningRate, int maxIterations) {
        this.learningRate = learningRate;
        this.maxIterations = maxIterations;
        this.coefficients = new ArrayList<>();
    }

    @Override
    public void train(List<Double> x_train, List<Double> y_train) {
        // Initialize coefficients and intercept
        for (int i = 0; i < x_train.size() + 1; i++) {
            coefficients.add(0.0);
        }

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            List<Double> predictions = predict(x_train);
            // Update coefficients and intercept
            double interceptGradient = 0.0;
            List<Double> slopeGradients = new ArrayList<>(coefficients);

            for (int i = 0; i < slopeGradients.size(); i++) {
                slopeGradients.set(i, 0.0);
            }

            for (int i = 0; i < x_train.size(); i++) {
                double predicted = predictions.get(i);
                interceptGradient += predicted - y_train.get(i);
                for (int j = 0; j < coefficients.size() - 1; j++) {
                    slopeGradients.set(j, slopeGradients.get(j) + (predicted - y_train.get(i)) * x_train.get(i));
                }
            }

            intercept -= learningRate * interceptGradient / x_train.size();
            for (int i = 0; i < coefficients.size() - 1; i++) {
                coefficients.set(i, coefficients.get(i) - learningRate * slopeGradients.get(i) / x_train.size());
            }
        }
    }

    @Override
    public List<Double> predict(List<Double> x_test) {
        List<Double> predictions = new ArrayList<>();
        for (double x : x_test) {
            double linearCombination = intercept;
            for (int i = 0; i < coefficients.size() - 1; i++) {
                linearCombination += coefficients.get(i) * x;
            }
            double probability = 1 / (1 + Math.exp(-linearCombination));
            predictions.add(probability);
        }
        return predictions;
    }

    @Override
    public double evaluate(List<Double> x_test, List<Double> y_test) {
        // This method would typically calculate a classification metric (e.g., accuracy)
        // For simplicity, let's assume it's not implemented yet.
        throw new UnsupportedOperationException("Evaluate method not implemented.");
    }

    private double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }
}
