package raf.graffito.dsw.model.observer;

public class LoggerFactory {

    public static Logger createLogger(String type){
        switch (type.toLowerCase()) {
            case "console":
                return new ConsoleLogger();
            case "file":
                return new FileLogger();
            default:
                throw new IllegalArgumentException("Unknown" + type);
        }
    }
}
