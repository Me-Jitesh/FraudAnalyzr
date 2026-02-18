package com.jitesh.fraudanalyzr.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogisticFraudModel {

    @Value("${ml.fraud.bias}")
    private double bias;

    @Value("${ml.fraud.amount-weight}")
    private double amountWeight;

    @Value("${ml.fraud.velocity-weight}")
    private double velocityWeight;

    public double predictProbability(double amount, int velocityFlag) {

        double z = bias
                + (amountWeight * amount)
                + (velocityWeight * velocityFlag);

        return sigmoid(z);
    }

    public int predictRiskScore(double amount, int velocityFlag) {

        double probability = predictProbability(amount, velocityFlag);

        return (int) Math.round(probability * 100);
    }

    private double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }
}
