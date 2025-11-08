package raf.graffito.dsw.gui.swing;



import raf.graffito.dsw.controller.*;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar(ActionManager actionManager) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        AboutUsAction aboutUsAction = actionManager.getAboutUsAction();
        ExitAction exitAction = actionManager.getExitAction();
        AddAction addAction = actionManager.getAddAction();
        DeleteAction deleteAction = actionManager.getDeleteAction();
        RenameAction renameAction = actionManager.getRenameAction();
        EditProjectAction editProjectAction = actionManager.getEditProjectAction();
        HelpAction helpAction = actionManager.getHelpAction();
        fileMenu.add(exitAction);
        fileMenu.add(aboutUsAction);
        fileMenu.add(addAction);
        fileMenu.add(deleteAction);
        fileMenu.add(renameAction);
        fileMenu.add(editProjectAction);
        fileMenu.add(helpAction);

        add(fileMenu);
    }
}
