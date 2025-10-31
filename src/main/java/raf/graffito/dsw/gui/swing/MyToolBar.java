package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.*;

import javax.swing.*;
import java.awt.*;

public class MyToolBar extends JToolBar {
    public MyToolBar(ActionManager actionManager) {
        super(HORIZONTAL);
        setFloatable(false);

        addActionWithText(actionManager.getExitAction());
        addActionWithText(actionManager.getAboutUsAction());
        addActionWithText(actionManager.getAddAction());
        addActionWithText(actionManager.getRenameAction());
        addActionWithText(actionManager.getDeleteAction());
        addActionWithText(actionManager.getEditProjectAction());

    }

    private void addActionWithText(Action action) {
        JButton button = add(action);
        button.setHideActionText(false);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setMargin(new Insets(5, 3, 6, 8));
        button.setPreferredSize(new Dimension(100, 30));

        addSeparator(new Dimension(7, 0));
    }
}
