package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.ActionManager;
import raf.graffito.dsw.controller.SetAddTypeAction;
import raf.graffito.dsw.controller.SetStateAction;

import javax.swing.*;
import java.awt.*;

public class SlideEditToolBar extends JToolBar {

    public SlideEditToolBar(ActionManager actionManager) {
        super(HORIZONTAL);
        setFloatable(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        addStateActionWithText(actionManager.getSelectStateAction());
        addStateActionWithText(actionManager.getMoveStateAction());
        addStateActionWithText(actionManager.getResizeStateAction());
        addStateActionWithText(actionManager.getDeleteStateAction());
        addActionWithText(actionManager.getDuplicateAction());

        add(Box.createHorizontalGlue());

        addSetAddTypeActionWithText(actionManager.getAddTextAction());
        addActionWithText(actionManager.getAddImageAction());
        addActionWithText(actionManager.getAddLogoAction());

        add(Box.createHorizontalGlue());

        addActionWithText(actionManager.getRotateLeftAction());
        addActionWithText(actionManager.getRotateRightAction());
        addStateActionWithText(actionManager.getZoomStateAction());

        actionManager.updateStateButtonSelection("select");
    }

    private void addStateActionWithText(SetStateAction action) {
        JButton button = new JButton(action);
        button.setFocusPainted(false);
        button.setHideActionText(false);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setMargin(new Insets(5, 3, 6, 8));

        Dimension size = new Dimension(20, 30);
        button.setMaximumSize(size);

        action.setButton(button);
        add(button);
    }

    private void addSetAddTypeActionWithText(SetAddTypeAction action) {
        JButton button = new JButton(action);
        button.setFocusPainted(false);
        button.setHideActionText(false);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setMargin(new Insets(5, 3, 6, 8));

        Dimension size = new Dimension(20, 30);
        button.setMaximumSize(size);

        action.setButton(button);
        add(button);
    }

    private void addActionWithText(Action action) {
        JButton button = new JButton(action);
        button.setFocusPainted(false);
        button.setHideActionText(false);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setMargin(new Insets(5, 3, 6, 8));

        Dimension size = new Dimension(20, 30);
        button.setMaximumSize(size);
        add(button);
    }
}
