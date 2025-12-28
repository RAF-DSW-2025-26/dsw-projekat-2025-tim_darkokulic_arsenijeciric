package raf.graffito.dsw.controller;

import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.Slide;

import javax.swing.*;
import java.awt.*;

public class SlideController {

    private PresentationView presentationView;
    private SlideView currentSlideView;
    private Slide currentSlide;

    public SlideController(PresentationView presentationView) {
        this.presentationView = presentationView;
    }

    public void openSlide(Slide slide) {
        if (slide == null) return;

        this.currentSlide = slide;

        if (currentSlideView == null || currentSlideView.getSlide() != slide) {
            currentSlideView = new SlideView(slide);
            slide.addSubscriber(currentSlideView);
        }

        JTabbedPane tabbedPane = presentationView.getTabbedPane();

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component component = tabbedPane.getComponentAt(i);
            if (component instanceof JPanel panel) {
                Object tabPresentation = panel.getClientProperty("presentation");
                if (tabPresentation != null && slide.getParent() == tabPresentation) {
                    panel.removeAll();
                    panel.setLayout(new BorderLayout());

                    JScrollPane scrollPane = new JScrollPane(currentSlideView);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    panel.add(scrollPane, BorderLayout.CENTER);

                    panel.revalidate();
                    panel.repaint();

                    tabbedPane.setSelectedIndex(i);
                    break;
                }
            }
        }

        updateUndoRedoButtonStates();
    }

    private void updateUndoRedoButtonStates() {
        MainFrame mainFrame = MainFrame.getInstance();
        if (mainFrame != null && mainFrame.getActionManager() != null) {
            mainFrame.getActionManager().updateUndoRedoButtonStates();
        }
    }

    public SlideView getCurrentSlideView() {
        return currentSlideView;
    }

    public Slide getCurrentSlide() {
        return currentSlide;
    }

    public boolean isSlideOpen(Slide slide) {
        return currentSlide == slide;
    }
}
