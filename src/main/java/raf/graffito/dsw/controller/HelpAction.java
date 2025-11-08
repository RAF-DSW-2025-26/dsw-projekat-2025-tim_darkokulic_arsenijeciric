package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.HelpDialog;
import raf.graffito.dsw.gui.swing.MainFrame;

import java.awt.event.ActionEvent;

public class HelpAction extends AbstractGraffAction {

    public HelpAction() {
        putValue(NAME, "Help");
        putValue(SHORT_DESCRIPTION, "Open help dialog");
        putValue(SMALL_ICON, loadIcon("/images/help.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        HelpDialog dialog = new HelpDialog(MainFrame.getInstance());
        dialog.setVisible(true);
    }
}
