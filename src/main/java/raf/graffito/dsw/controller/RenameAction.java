package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.GraffTree;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RenameAction extends AbstractGraffAction {

    private GraffTree graffTree;
    private JFrame parent;

    public RenameAction(GraffTree graffTree, JFrame parent) {

        this.graffTree = graffTree;
        this.parent = parent;

        putValue(NAME, "Rename");
        putValue(SHORT_DESCRIPTION, "Rename selected node");
        Icon icon = loadIcon("/images/rename.png");
        putValue(SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffNode selected = graffTree.getSelectedNode();
        if (selected == null) return;

        Icon customIcon = null;
        java.net.URL imageURL = getClass().getResource("/images/edit.png");
        if (imageURL != null) {
            java.awt.Image img = new ImageIcon(imageURL).getImage();
            java.awt.Image scaledImg = img.getScaledInstance(48, 48, java.awt.Image.SCALE_SMOOTH);
            customIcon = new ImageIcon(scaledImg);
        }

        // custom JOptionPane prozor za promenu imena sa custom ikonicom
        String newName = (String) JOptionPane.showInputDialog(
                parent,
                "Enter new name:",
                "Rename",
                JOptionPane.PLAIN_MESSAGE,
                customIcon,
                null,
                selected.getNodeName()
        );

        if (newName != null && !newName.isBlank()) {
            graffTree.renameNode(selected, newName);

            // update-uje desni panel sa informacijama da je ime promenjeno
            if (selected instanceof Project project) {
                JTabbedPane tabbedPane = MainFrame.getInstance().getTabbedPane();
                Presentation currentPresentation = null;

                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Component component = tabbedPane.getComponentAt(selectedIndex);
                    if (component instanceof JPanel panel) {
                        currentPresentation = (Presentation) panel.getClientProperty("presentation");
                    }
                }

                MainFrame.getInstance().updateSelected(project, currentPresentation);
            }
        }
    }
}
