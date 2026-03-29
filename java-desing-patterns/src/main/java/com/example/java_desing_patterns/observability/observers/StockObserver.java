package com.example.java_desing_patterns.observability.observers;

import com.example.java_desing_patterns.observability.Order;
import com.example.java_desing_patterns.observability.OrderObserver;

public class StockObserver implements OrderObserver {
    @Override
    public void onConfirmedOrder(Order order) {
        System.out.println("Stock control: removing item from inventory " + order.toString());
    }
}
