package raf.graffito.dsw.core;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.Logger;
import raf.graffito.dsw.model.LoggerFactory;
import raf.graffito.dsw.model.MessageGenerator;

public class ApplicationFramework {
    // Buduća polja za model celog projekta
    private static ApplicationFramework instance;
    private final MessageGenerator messageGenerator;
    private final LoggerFactory loggerFactory;

    private ApplicationFramework(){
        initialize();
        this.messageGenerator = new MessageGenerator();
        this.loggerFactory = new LoggerFactory();
        addLoggers();

    }

    public static ApplicationFramework getInstance() {
        if (instance == null) {
            instance = new ApplicationFramework();
        }
        return instance;
    }
        public void initialize(){
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);
    }

    private void addLoggers() {
        Logger consoleLogger = LoggerFactory.createLogger("console");
        Logger fileLogger = LoggerFactory.createLogger("file");

        messageGenerator.addSubscriber(consoleLogger);
        messageGenerator.addSubscriber(fileLogger);
        messageGenerator.addSubscriber(MainFrame.getInstance());
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public LoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

}
