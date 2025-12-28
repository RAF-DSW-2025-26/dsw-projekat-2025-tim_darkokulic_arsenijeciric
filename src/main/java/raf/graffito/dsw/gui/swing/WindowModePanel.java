package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.ActionManager;

import javax.swing.*;
import java.awt.*;

public class WindowModePanel extends JPanel {

    private JRadioButton normalRadioButton;
    private JRadioButton fullscreenRadioButton;
    private JRadioButton smallRadioButton;

    public WindowModePanel(ActionManager actionManager) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setBorder(BorderFactory.createTitledBorder("Window Mode"));

        normalRadioButton = new JRadioButton(actionManager.getNormalModeAction());
        fullscreenRadioButton = new JRadioButton(actionManager.getFullscreenModeAction());
        smallRadioButton = new JRadioButton(actionManager.getSmallModeAction());

        normalRadioButton.setSelected(true);

        ButtonGroup modeGroup = new ButtonGroup();

        modeGroup.add(fullscreenRadioButton);
        modeGroup.add(normalRadioButton);
        modeGroup.add(smallRadioButton);

        add(fullscreenRadioButton);
        add(normalRadioButton);
        add(smallRadioButton);
    }
}
