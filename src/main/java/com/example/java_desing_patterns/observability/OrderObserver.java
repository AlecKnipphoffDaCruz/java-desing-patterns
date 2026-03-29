package com.example.java_desing_patterns.observability;

public interface OrderObserver {
    void onConfirmedOrder(Order order);
}
