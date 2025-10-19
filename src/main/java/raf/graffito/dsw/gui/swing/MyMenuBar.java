package raf.graffito.dsw.gui.swing;



import raf.graffito.dsw.controller.AboutUsAction;
import raf.graffito.dsw.controller.ActionManager;
import raf.graffito.dsw.controller.ExitAction;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar(ActionManager actionManager) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        ExitAction exitAction = actionManager.getExitAction();
        fileMenu.add(exitAction);
        AboutUsAction aboutUsAction = actionManager.getAboutUsAction();
        fileMenu.add(aboutUsAction);
        add(fileMenu);
    }
}
