package com.example.java_desing_patterns.observability.observers;

import com.example.java_desing_patterns.observability.Order;
import com.example.java_desing_patterns.observability.OrderObserver;

public class FNObserver implements OrderObserver {

    @Override
    public void onConfirmedOrder(Order order) {
        System.out.println("Generating Fiscal Note of order:" + order );
    }
}
