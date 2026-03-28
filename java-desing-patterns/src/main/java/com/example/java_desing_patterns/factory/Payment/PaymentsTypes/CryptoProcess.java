package com.example.java_desing_patterns.factory.Payment.PaymentsTypes;

import org.springframework.stereotype.Service;

import com.example.java_desing_patterns.factory.Payment.PaymentProcess;

@Service("crypto")
public class CryptoProcess implements PaymentProcess {
    public void processPayment ( Double payment){
        System.out.println("Processing Crypto payment of amount: " + payment);
    }
}
