package main.java.com.example.java_desing_patterns.decorator;

public abstract class NotificatorDecorator implements Notificator {
    protected final Notificator wrapped;
    public NotificatorDecorator(Notificator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void send(String message) {
        wrapped.send(message);
    }
}
