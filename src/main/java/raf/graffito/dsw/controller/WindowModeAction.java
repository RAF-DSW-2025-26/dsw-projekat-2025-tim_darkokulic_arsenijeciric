package raf.graffito.dsw.controller;

import raf.graffito.dsw.model.windowmode.WindowModeManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class WindowModeAction extends AbstractGraffAction {

    private final WindowModeManager windowModeManager;
    private final String modeName;

    public WindowModeAction(WindowModeManager windowModeManager, String modeName) {
        this.windowModeManager = windowModeManager;
        this.modeName = modeName;
        putValue(NAME, modeName);
        putValue(SHORT_DESCRIPTION, "Switch to " + modeName + " mode");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        windowModeManager.setMode(modeName);
    }
}
