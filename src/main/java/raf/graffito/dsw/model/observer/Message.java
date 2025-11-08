package raf.graffito.dsw.model.observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private final String content;
    private final String type;
    private final String timestamp;


    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");

    public Message(String content, String type) {
        this.content = content;
        this.type = type.toUpperCase();
        this.timestamp = LocalDateTime.now().format(format);
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
