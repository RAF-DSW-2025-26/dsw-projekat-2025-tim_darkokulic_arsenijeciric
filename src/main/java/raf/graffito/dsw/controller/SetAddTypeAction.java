package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class SetAddTypeAction extends AbstractGraffAction {

    private String elementType;

    public SetAddTypeAction(String elementType, String displayName) {
        this.elementType = elementType;
        putValue(NAME, displayName);
        putValue(SHORT_DESCRIPTION, "Add " + displayName);

        switch (elementType) {
            case "text" -> putValue(SMALL_ICON, loadIcon("/images/insert_text.png"));
            case "image" -> putValue(SMALL_ICON, loadIcon("/images/insert_image.png"));
            case "logo" -> putValue(SMALL_ICON, loadIcon("/images/insert_logo.png"));
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

        switch (elementType) {
            case "text" -> {
                currentSlideView.getStateManager().getAddState().addTextElement(currentSlideView);
                currentSlideView.getStateManager().setSelectState();
            }
            case "image" -> {
                BufferedImage selectedImage = MainFrame.getInstance().getProjectView().getSelectedImage();
                int imageIndex = MainFrame.getInstance().getProjectView().getSelectedImageIndex();
                currentSlideView.getStateManager().getAddState().addImageElement(
                        selectedImage, imageIndex, currentSlideView
                );
                currentSlideView.getStateManager().setSelectState();
            }
            case "logo" -> {
                currentSlideView.getStateManager().getAddState().addLogoElement(currentSlideView);
                currentSlideView.getStateManager().setSelectState();
            }
        }

        currentSlideView.requestFocusInWindow();
    }
}
