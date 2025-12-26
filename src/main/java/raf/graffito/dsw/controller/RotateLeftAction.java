package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.SlideElement;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RotateLeftAction extends AbstractGraffAction {

    public RotateLeftAction() {
        putValue(NAME, "Rotate Left");
        putValue(SHORT_DESCRIPTION, "Rotate selected elements 90° left");
        putValue(SMALL_ICON, loadIcon("/images/rotate_left.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SlideController slideController = MainFrame.getInstance()
                .getActionManager()
                .getSlideController();

        SlideView currentSlideView = slideController.getCurrentSlideView();
        if (currentSlideView == null) return;

        for (SlideElement element : currentSlideView.getSelectedElements()) {
            double currentRotation = element.getRotation();
            element.setRotation(currentRotation - Math.PI / 2);
        }

        currentSlideView.repaint();
    }
}
