package raf.graffito.dsw.controller;

import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractGraffAction {

    public RedoAction() {
        putValue(NAME, "Redo");
        putValue(SHORT_DESCRIPTION, "Redo last undone action");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SlideController slideController = MainFrame.getInstance()
                .getActionManager()
                .getSlideController();

        SlideView currentSlideView = slideController.getCurrentSlideView();
        if (currentSlideView == null) return;

        if (currentSlideView.getUndoRedoManager().redo()) {
            currentSlideView.repaint();
            MainFrame.getInstance().getActionManager().updateUndoRedoButtonStates();
        }
    }
}

