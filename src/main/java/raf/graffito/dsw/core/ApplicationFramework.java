package raf.graffito.dsw.core;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.repository.GraffRepository;
import raf.graffito.dsw.model.observer.Logger;
import raf.graffito.dsw.model.observer.LoggerFactory;
import raf.graffito.dsw.model.observer.MessageGenerator;
import raf.graffito.dsw.model.Workspace;

import javax.swing.*;
import java.awt.*;

public class ApplicationFramework {
    // Buduća polja za model celog projekta
    private static ApplicationFramework instance;
    private static boolean isInitializing = false;
    private final MessageGenerator messageGenerator;
    private final LoggerFactory loggerFactory;
    private final GraffRepository repository;

    private ApplicationFramework() {
        if (isInitializing) {
            throw new IllegalStateException("Circular initialization detected!");
        }
        isInitializing = true;

        Workspace workspace = new Workspace("My Workspace");
        this.repository = new GraffRepository(workspace);

        this.messageGenerator = repository.getMessageGenerator();
        this.loggerFactory = new LoggerFactory();

        addLoggers();
        initialize();

        isInitializing = false;
    }


    public static ApplicationFramework getInstance() {
        if (instance == null) {
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public void initialize(){
        Font customFont = loadCustomFont("/fonts/Inter-Bold.ttf");
        setUIFont(customFont.deriveFont(12f));


        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.finishInitialization(repository, messageGenerator);
        messageGenerator.addSubscriber(mainFrame);
        mainFrame.setVisible(true);
    }

    private void addLoggers() {
        Logger consoleLogger = LoggerFactory.createLogger("console");
        Logger fileLogger = LoggerFactory.createLogger("file");

        messageGenerator.addSubscriber(consoleLogger);
        messageGenerator.addSubscriber(fileLogger);
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public LoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    public GraffRepository getRepository() {
        return repository;
    }

    private void setUIFont(Font font) {
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("Tree.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("Panel.font", font);
    }

    private Font loadCustomFont(String fontPath) {
        try {
            Font customFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(fontPath)
            );

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            System.out.println("Font loaded successfully: " + customFont.getFontName());

            return customFont;

        } catch (Exception e) {
            System.err.println("Failed to load font: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
