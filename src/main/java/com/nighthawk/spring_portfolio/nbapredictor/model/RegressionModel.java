package com.nighthawk.spring_portfolio.nbapredictor.model;

import java.util.List;

public abstract class RegressionModel {

    protected List<Double> coefficients;
    protected double intercept;

    public abstract void train(List<Double> x_train, List<Double> y_train);
    public abstract List<Double> predict(List<Double> x_test);
    public abstract double evaluate(List<Double> x_test, List<Double> y_test);

    public List<Double> getCoefficients() {
        return coefficients;
    }

    public double getIntercept() {
        return intercept;
    }

    /**
     * Method to calculate the Mean Squared Error (MSE).
     * @param actual The actual values.
     * @param predicted The predicted values.
     * @return The MSE value.
     */
    protected double meanSquaredError(List<Double> actual, List<Double> predicted) {
        double sum = 0.0;
        if(actual.size() != predicted.size()) {
            throw new IllegalArgumentException("The size of actual and predicted lists must be the same.");
        }
        for (int i = 0; i < actual.size(); i++) {
            sum += Math.pow(actual.get(i) - predicted.get(i), 2);
        }
        return sum / actual.size();
    }

    protected double rSquared(List<Double> actual, List<Double> predicted) {
        double sumOfSquaredTotal = 0.0;
        double sumOfSquaredResidual = 0.0;
        double meanActual = actual.stream().mapToDouble(val -> val).average().orElse(0.0);

        for (int i = 0; i < actual.size(); i++) {
            sumOfSquaredTotal += Math.pow(actual.get(i) - meanActual, 2);
            sumOfSquaredResidual += Math.pow(actual.get(i) - predicted.get(i), 2);
        }

        return 1 - (sumOfSquaredResidual / sumOfSquaredTotal);
    }    
}