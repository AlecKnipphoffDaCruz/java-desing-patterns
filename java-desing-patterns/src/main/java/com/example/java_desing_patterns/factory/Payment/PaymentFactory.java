package com.example.java_desing_patterns.factory.Payment;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class PaymentFactory {
    private final Map<String, PaymentProcess> processators;

    public PaymentFactory(Map<String, PaymentProcess> processators) {
        this.processators = processators;
    }

    public PaymentProcess create(String type){
        PaymentProcess p = processators.get(type);
        if(p == null) throw new IllegalArgumentException("Invalid type: " + type);
        return p;
    }
}
