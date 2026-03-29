package com.example.java_desing_patterns.observability.observers;

import com.example.java_desing_patterns.observability.Order;
import com.example.java_desing_patterns.observability.OrderObserver;

public class EmailObserver implements OrderObserver {
    @Override
    public void onConfirmedOrder(Order order) {
        System.out.println("Sending email with observer: " + order.toString());
    }
}
