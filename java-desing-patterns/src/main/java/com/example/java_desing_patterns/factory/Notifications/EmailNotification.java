package com.example.java_desing_patterns.factory.Notifications;

public class EmailNotification implements Notification {

    public void send(String message){
        System.out.println("Sending email notification: " + message);
    }

    
}
