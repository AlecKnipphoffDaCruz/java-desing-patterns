package com.example.java_desing_patterns.factory.Payment.PaymentsTypes;

import org.springframework.stereotype.Service;

import com.example.java_desing_patterns.factory.Payment.PaymentProcess;

@Service("card")
public class CardProcess implements PaymentProcess{
    public void processPayment(Double payment){
        System.out.println("Processing card payment of amount: " + payment);
    }
}
