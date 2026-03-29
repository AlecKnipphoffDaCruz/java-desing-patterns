package com.example.java_desing_patterns.strategy;

public class VIPDiscont implements DiscontStrategy {
    @Override
    public double apply(double value) {
        return value * 0.8;
    }
}
