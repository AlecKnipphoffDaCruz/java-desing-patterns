package com.example.java_desing_patterns.factory.Notifications;

public class SMSNotification implements Notification {

    public void send(String message){
        System.out.println("Sending SMS notification: " + message);
    }
    
}
