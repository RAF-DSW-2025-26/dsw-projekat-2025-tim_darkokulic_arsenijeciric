package raf.graffito.dsw.model.observer;

public class ConsoleLogger implements Logger {

    @Override
    public void update(Message message) {
        System.out.println("[" + message.getType() + "][" + message.getTimestamp() + "] " + message.getContent());
    }

}
