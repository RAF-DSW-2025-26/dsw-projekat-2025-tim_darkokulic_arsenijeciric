package raf.graffito.dsw.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger {
    private static final String logFile = "src/main/resources/log.txt";

    @Override
    public void update(Message message) {
        String logEntry = String.format("[%s][%s] %s%n",
                message.getType(),
                message.getTimestamp(),
                message.getContent());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Greska" + e.getMessage());
        }
    }

}
