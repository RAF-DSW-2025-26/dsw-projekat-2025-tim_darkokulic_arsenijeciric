package raf.graffito.dsw.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger implements Logger {

    @Override
    public void update(Message message) {
        System.out.println("[" + message.getType() + "][" + message.getTimestamp() + "] " + message.getContent());
    }
}
