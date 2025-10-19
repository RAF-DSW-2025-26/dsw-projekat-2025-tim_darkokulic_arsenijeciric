package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.AboutUsDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AboutUsAction extends AbstractGraffAction {

    public AboutUsAction() {
        putValue(NAME, "About Us");
        putValue(SHORT_DESCRIPTION, "About Us");
        Icon icon = loadIcon("/images/about_us.png");
        putValue(SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Otvara poseban dijalog prozor koji je opisan u gui.swing klasi AboutUsDialog
        AboutUsDialog dialog = new AboutUsDialog();
        dialog.setVisible(true);
    }
}
