package com.example.java_desing_patterns.factory.Payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentFactory paymentFactory;

    public PaymentService(PaymentFactory paymentFactory) {
        this.paymentFactory = paymentFactory;
    }

    public void processPayment(String type, double amount){
        PaymentProcess process = paymentFactory.create(type);
        process.processPayment(amount);
    }
}
