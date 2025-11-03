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
    private GraffRepository repository;

    public RenameAction(GraffTree graffTree, JFrame parent, GraffRepository repository) {

        this.graffTree = graffTree;
        this.parent = parent;
        this.repository = repository;

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
            if (repository.renameNode(selected, newName)) {
                graffTree.renameNode(selected, newName);

                // Update views if needed
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
}