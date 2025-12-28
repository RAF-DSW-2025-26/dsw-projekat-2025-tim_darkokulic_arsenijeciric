package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.SlideElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class DuplicateAction extends AbstractGraffAction {

    private static final int DUPLICATE_OFFSET = 20;

    public DuplicateAction() {
        putValue(NAME, "Duplicate");
        putValue(SHORT_DESCRIPTION, "Duplicate selected elements");
        putValue(SMALL_ICON, loadIcon("/images/duplicate.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SlideController slideController = MainFrame.getInstance()
                .getActionManager()
                .getSlideController();

        SlideView currentSlideView = slideController.getCurrentSlideView();
        if (currentSlideView == null) return;

        Slide currentSlide = slideController.getCurrentSlide();
        if (currentSlide == null) return;

        List<SlideElement> selectedElements = currentSlideView.getSelectedElements();
        if (selectedElements.isEmpty()) return;

        List<SlideElement> duplicatedElements = new ArrayList<>();

        for (SlideElement element : selectedElements) {
            SlideElement duplicate = element.copy();

            Point newPosition = new Point(
                duplicate.getPosition().x + DUPLICATE_OFFSET,
                duplicate.getPosition().y + DUPLICATE_OFFSET
            );
            duplicate.setPosition(newPosition);

            currentSlide.addElement(duplicate);
            duplicatedElements.add(duplicate);
        }

        currentSlideView.getSelectedElements().clear();
        currentSlideView.getSelectedElements().addAll(duplicatedElements);

        currentSlideView.repaint();
    }
}
