package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.command.AddElementCommand;
import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.ImageElement;
import raf.graffito.dsw.model.LabelElement;
import raf.graffito.dsw.model.LogoElement;
import raf.graffito.dsw.model.Slide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class AddState implements State {

    private String logoType = "circle";

    @Override
    public void mouseClicked(MouseEvent e, SlideView slideView) {
    }

    public void addTextElement(SlideView slideView) {
        String text = JOptionPane.showInputDialog(
                slideView,
                "Enter text:",
                "Add Text",
                JOptionPane.PLAIN_MESSAGE
        );

        if (text != null && !text.trim().isEmpty()) {
            Point centerPosition = new Point(
                    SlideView.SLIDE_WIDTH / 2 - 100,
                    SlideView.SLIDE_HEIGHT / 2 - 25
            );

            LabelElement label = new LabelElement(
                    centerPosition,
                    new Dimension(200, 50),
                    0,
                    text
            );

            Slide slide = slideView.getSlide();
            AddElementCommand addCommand = new AddElementCommand(slide, label);
            UndoRedoManager manager = slideView.getUndoRedoManager();
            manager.execute(addCommand);
            slideView.repaint();
            updateButtonStates();
        }
    }

    public void addImageElement(BufferedImage image, int imageIndex, SlideView slideView) {
        if (image == null) {
            JOptionPane.showMessageDialog(
                    slideView,
                    "Please select an image from the Project View first.",
                    "No Image Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        int maxSize = 400;
        int scaledWidth = originalWidth;
        int scaledHeight = originalHeight;

        if (originalWidth > maxSize || originalHeight > maxSize) {
            double scale = Math.min((double) maxSize / originalWidth, (double) maxSize / originalHeight);
            scaledWidth = (int) (originalWidth * scale);
            scaledHeight = (int) (originalHeight * scale);
        }

        Point centerPosition = new Point(
                SlideView.SLIDE_WIDTH / 2 - scaledWidth / 2,
                SlideView.SLIDE_HEIGHT / 2 - scaledHeight / 2
        );

        ImageElement imageElement = new ImageElement(
                centerPosition,
                new Dimension(scaledWidth, scaledHeight),
                0,
                image,
                imageIndex
        );

        Slide slide = slideView.getSlide();
        AddElementCommand addCommand = new AddElementCommand(slide, imageElement);
        UndoRedoManager manager = slideView.getUndoRedoManager();
        manager.execute(addCommand);
        slideView.repaint();
        updateButtonStates();
    }

    public void addLogoElement(SlideView slideView) {
        Point centerPosition = new Point(
                SlideView.SLIDE_WIDTH / 2 - 50,
                SlideView.SLIDE_HEIGHT / 2 - 50
        );

        LogoElement logo = new LogoElement(
                centerPosition,
                new Dimension(100, 100),
                0,
                logoType
        );

        Slide slide = slideView.getSlide();
        AddElementCommand addCommand = new AddElementCommand(slide, logo);
        UndoRedoManager manager = slideView.getUndoRedoManager();
        manager.execute(addCommand);
        slideView.repaint();
        updateButtonStates();
    }
    
    private void updateButtonStates() {
        MainFrame mainFrame = MainFrame.getInstance();
        if (mainFrame != null && mainFrame.getActionManager() != null) {
            mainFrame.getActionManager().updateUndoRedoButtonStates();
        }
    }

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseMoved(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseWheelMoved(int rotation, MouseEvent e, SlideView slideView) {
    }

    @Override
    public String getStateName() {
        return "Add";
    }

    public void setLogoType(String logoType) {
        this.logoType = logoType;
    }
}
