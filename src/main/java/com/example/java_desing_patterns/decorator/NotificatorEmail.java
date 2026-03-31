package main.java.com.example.java_desing_patterns.decorator;

public class NotificatorEmail implements Notificator {

    @Override
    public void send(String message){
        System.out.println("Enviando email: " + message);
    }
}
