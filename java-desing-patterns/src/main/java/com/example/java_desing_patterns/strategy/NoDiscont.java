package com.example.java_desing_patterns.strategy;

public class NoDiscont implements DiscontStrategy {
    @Override
    public double apply(double value) {
        return value;
    }
}
