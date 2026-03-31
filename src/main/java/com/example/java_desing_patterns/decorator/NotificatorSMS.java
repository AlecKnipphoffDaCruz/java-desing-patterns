package main.java.com.example.java_desing_patterns.decorator;

public class NotificatorSMS implements Notificator {

    public NotificatorSMS(Notificator wrapped) {
        super(wrapped);
    }
 
    @Override
    public void send(String message){
        System.out.println("Enviando SMS: " + message);
    }
    
}
