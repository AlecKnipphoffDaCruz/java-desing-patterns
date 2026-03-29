package com.example.java_desing_patterns.factory.Notifications;

public class FactoryNotification {
  
    public static Notification createNotification(String type) {
        return switch (type.toLowerCase()){
            case "email" -> new EmailNotification();
            case "sms" -> new SMSNotification();
            default -> throw new IllegalArgumentException("Invalid notification type: " + type);
        };
    }
}
