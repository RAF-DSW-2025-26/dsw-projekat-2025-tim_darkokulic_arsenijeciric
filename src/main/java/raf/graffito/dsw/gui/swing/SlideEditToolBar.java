package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.ActionManager;

import javax.swing.*;
import java.awt.*;

public class SlideEditToolBar extends JToolBar {

    public SlideEditToolBar(ActionManager actionManager) {
        super(HORIZONTAL);
        setFloatable(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        addActionWithText(actionManager.getSelectStateAction());
        addActionWithText(actionManager.getMoveStateAction());
        addActionWithText(actionManager.getResizeStateAction());
        addActionWithText(actionManager.getDeleteStateAction());
        addActionWithText(actionManager.getZoomStateAction());

        add(Box.createHorizontalGlue());

        addActionWithText(actionManager.getAddTextAction());
        addActionWithText(actionManager.getAddImageAction());
        addActionWithText(actionManager.getAddLogoAction());

        add(Box.createHorizontalGlue());

        addActionWithText(actionManager.getRotateLeftAction());
        addActionWithText(actionManager.getRotateRightAction());
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
