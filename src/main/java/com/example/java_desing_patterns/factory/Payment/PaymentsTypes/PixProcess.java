package com.example.java_desing_patterns.factory.Payment.PaymentsTypes;

import org.springframework.stereotype.Service;

import com.example.java_desing_patterns.factory.Payment.PaymentProcess;

@Service("pix")
public class PixProcess implements PaymentProcess {
    public void processPayment(Double payment){
        System.out.println("Processing Pix payment of amount: " + payment);
    }
}
