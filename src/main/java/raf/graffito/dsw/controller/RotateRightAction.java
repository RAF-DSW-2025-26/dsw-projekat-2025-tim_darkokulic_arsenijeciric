package raf.graffito.dsw.controller;

import raf.graffito.dsw.command.RotateElementCommand;
import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.SlideElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

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
        if (currentSlideView == null || currentSlideView.getSelectedElements().isEmpty()) return;

        List<SlideElement> elements = new ArrayList<>(currentSlideView.getSelectedElements());
        double rotationDelta = Math.PI / 2;
        
        RotateElementCommand rotateCommand = new RotateElementCommand(elements, rotationDelta);
        UndoRedoManager manager = currentSlideView.getUndoRedoManager();
        manager.execute(rotateCommand);
        
        currentSlideView.repaint();
        MainFrame.getInstance().getActionManager().updateUndoRedoButtonStates();
    }
}
