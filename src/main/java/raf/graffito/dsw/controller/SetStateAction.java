package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SetStateAction extends AbstractGraffAction {

    private String stateName;

    public SetStateAction(String stateName, String displayName, String description) {
        this.stateName = stateName;
        putValue(NAME, displayName);
        putValue(SHORT_DESCRIPTION, description);

        switch (stateName) {
            case "select" -> putValue(SMALL_ICON, loadIcon("/images/select.png"));
            case "move" -> putValue(SMALL_ICON, loadIcon("/images/move.png"));
            case "resize" -> putValue(SMALL_ICON, loadIcon("/images/resize.png"));
            case "delete" -> putValue(SMALL_ICON, loadIcon("/images/delete_element.png"));
            case "zoom" -> putValue(SMALL_ICON, loadIcon("/images/zoom.png"));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SlideController slideController = MainFrame.getInstance()
                .getActionManager()
                .getSlideController();

        SlideView currentSlideView = slideController.getCurrentSlideView();
        if (currentSlideView == null) {
            JOptionPane.showMessageDialog(
                    MainFrame.getInstance(),
                    "Please open a slide first by double-clicking it in the tree.",
                    "No Slide Open",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        switch (stateName) {
            case "select" -> currentSlideView.getStateManager().setSelectState();
            case "move" -> currentSlideView.getStateManager().setMoveState();
            case "resize" -> currentSlideView.getStateManager().setResizeState();
            case "add" -> currentSlideView.getStateManager().setAddState();
            case "delete" -> currentSlideView.getStateManager().setDeleteState();
            case "zoom" -> currentSlideView.getStateManager().setZoomState();
        }

        currentSlideView.requestFocusInWindow();
    }
}
