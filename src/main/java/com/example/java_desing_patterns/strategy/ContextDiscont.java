package com.example.java_desing_patterns.strategy;

public class ContextDiscont {
    private DiscontStrategy discontStrategy;
    private double value;

    public ContextDiscont(double value, DiscontStrategy discontStrategy){
        this.value = value;
        this.discontStrategy = discontStrategy;
    }
    public double calculateDiscont(){
        return discontStrategy.apply(value);
    }

}
