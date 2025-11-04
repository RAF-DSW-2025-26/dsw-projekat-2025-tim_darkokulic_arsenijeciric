package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.GraffTree;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddAction extends AbstractGraffAction{

    private GraffTree graffTree;
    private GraffRepository repository;

    public AddAction(GraffTree graffTree, GraffRepository repository) {
        this.graffTree = graffTree;
        this.repository = repository;

        putValue(NAME, "Add");
        putValue(SHORT_DESCRIPTION, "Add a new element");
        Icon icon = loadIcon("/images/add.png");
        putValue(SMALL_ICON, icon);
    }

    /// poziva fabriku koja ce napraviti node po odgovarajucem parentu
    @Override
    public void actionPerformed(ActionEvent e) {
        GraffNode selected = graffTree.getSelectedNode();
        if (selected == null) return;

        GraffNode newNode = null;

        if (selected instanceof Workspace workspace) {
            newNode = repository.createNode(workspace, Project.class, "New Project", "Unknown");

        } else if (selected instanceof Project project) {
            newNode = repository.createNode(project, Presentation.class, "New Presentation");

            if (newNode instanceof Presentation presentation) {
                MainFrame.getInstance()
                        .getActionManager()
                        .getProjectController()
                        .addPresentationTab(presentation, project.getColor());
            }

        } else if (selected instanceof Presentation presentation) {
            newNode = repository.createNode(presentation, Slide.class, "New Slide", "Empty");
        }

        if (newNode != null) {
            graffTree.addChild(selected, newNode);
        }
    }
}