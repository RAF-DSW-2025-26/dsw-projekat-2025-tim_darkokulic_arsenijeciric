package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.EditProjectDialog;
import raf.graffito.dsw.gui.swing.GraffTree;
import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EditProjectAction extends AbstractGraffAction {

    private GraffTree graffTree;
    private JFrame parent;

    public EditProjectAction(GraffTree graffTree, JFrame parent) {
        this.graffTree = graffTree;
        this.parent = parent;

        putValue(NAME, "Edit Project");
        putValue(SHORT_DESCRIPTION, "Change Project Name and Author");
        Icon icon = loadIcon("/images/edit_project.png"); // choose your icon
        putValue(SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffNode selected = graffTree.getSelectedNode();
        if (selected == null || !(selected instanceof Project project)) return;

        EditProjectDialog dialog = new EditProjectDialog(parent, project);
        dialog.setVisible(true);

        if(dialog.isConfirmed()){
            String newName = dialog.getProjectName();
            String newAuthor = dialog.getProjectAuthor();

            if(!newName.isEmpty()) project.setName(newName);
            if(!newAuthor.isEmpty()) project.setAuthor(newAuthor);

            graffTree.getTree().updateUI();
        }
    }
}
