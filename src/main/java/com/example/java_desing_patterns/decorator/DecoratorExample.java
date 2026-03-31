package main.java.com.example.java_desing_patterns.decorator;

public class DecoratorExample {
 
    public static void main(String[] args) {
 
        System.out.println("--- Email only ---");
        Notificator n1 = new NotificatorEmail();
        n1.send("Order confirmed!");
 
        System.out.println("\n--- Email + SMS ---");
        Notificator n2 = new NotificatorSMS(
                             new NotificatorEmail());
        n2.send("Order confirmed!");
 
        System.out.println("\n--- Email + SMS + Slack ---");
        Notificator n3 = new NotificatorSlack(
                             new NotificatorSMS(
                                 new NotificatorEmail()));
        n3.send("Order confirmed!");
    }
}
 