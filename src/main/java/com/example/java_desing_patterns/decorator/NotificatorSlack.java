package main.java.com.example.java_desing_patterns.decorator;

public class NotificatorSlack implements Notificator {

    public NotificatorSlack(Notificator wrapped) {
        super(wrapped);
    }

    @Override
    public void send(String message){
        System.out.println("Enviando mensagem para o Slack: " + message);
    }
}