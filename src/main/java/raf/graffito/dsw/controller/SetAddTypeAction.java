package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class SetAddTypeAction extends AbstractGraffAction {

    private String elementType;
    private JButton button;

    public SetAddTypeAction(String elementType, String displayName) {
        this.elementType = elementType;
        putValue(NAME, displayName);
        putValue(SHORT_DESCRIPTION, "Add " + displayName);
        putValue(SELECTED_KEY, false);

        switch (elementType) {
            case "text" -> putValue(SMALL_ICON, loadIcon("/images/insert_text.png"));
            case "image" -> putValue(SMALL_ICON, loadIcon("/images/insert_image.png"));
            case "logo" -> putValue(SMALL_ICON, loadIcon("/images/insert_logo.png"));
        }
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public void setSelected(boolean selected) {
        putValue(SELECTED_KEY, selected);
        if (button != null) {
            if (selected) {
                button.setBackground(new Color(198, 198, 198));
                button.setOpaque(true);
            } else {
                button.setBackground(UIManager.getColor("Button.background"));
                button.setOpaque(false);
            }
        }
    }

    public String getElementType() {
        return elementType;
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

        switch (elementType) {
            case "text" -> {
                currentSlideView.getStateManager().setAddState();
                currentSlideView.getStateManager().getAddState().startWaitingForTextPosition();
                MainFrame.getInstance().getActionManager().updateStateButtonSelection("add-text");
            }
            case "image" -> {
                BufferedImage selectedImage = MainFrame.getInstance().getProjectView().getSelectedImage();
                int imageIndex = MainFrame.getInstance().getProjectView().getSelectedImageIndex();
                currentSlideView.getStateManager().getAddState().addImageElement(
                        selectedImage, imageIndex, currentSlideView
                );
                currentSlideView.getStateManager().setSelectState();
                MainFrame.getInstance().getActionManager().updateStateButtonSelection("select");
            }
            case "logo" -> {
                currentSlideView.getStateManager().getAddState().addLogoElement(currentSlideView);
                currentSlideView.getStateManager().setSelectState();
                MainFrame.getInstance().getActionManager().updateStateButtonSelection("select");
            }
        }

        currentSlideView.requestFocusInWindow();
    }
}
