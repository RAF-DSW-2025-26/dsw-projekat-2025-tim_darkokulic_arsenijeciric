package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.SlideElement;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RotateRightAction extends AbstractGraffAction {

    public RotateRightAction() {
        putValue(NAME, "Rotate Right");
        putValue(SHORT_DESCRIPTION, "Rotate selected elements 90° right");
        putValue(SMALL_ICON, loadIcon("/images/rotate_right.png"));
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
            element.setRotation(currentRotation + Math.PI / 2);
        }

        currentSlideView.repaint();
    }
}
