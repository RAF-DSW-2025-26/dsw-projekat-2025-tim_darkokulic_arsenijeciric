package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.GraffTree;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddAction extends AbstractGraffAction{

    private GraffTree graffTree;

    public AddAction(GraffTree graffTree) {

        this.graffTree = graffTree;

        putValue(NAME, "Add");
        putValue(SHORT_DESCRIPTION, "Add a new element");
        Icon icon = loadIcon("/images/add.png");
        putValue(SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GraffNode selected = graffTree.getSelectedNode();

        if (selected instanceof Workspace workspaceNode) {
            Project newProject = new Project(workspaceNode, "New Project", "Unknown");
            workspaceNode.addChild(newProject);
            graffTree.addChild(workspaceNode, newProject);

        } else if (selected instanceof Project projectNode) {
            Presentation newPresentation = new Presentation(projectNode, "New Presentation");
            projectNode.addChild(newPresentation);
            graffTree.addChild(projectNode, newPresentation);

            MainFrame.getInstance()
                    .getActionManager()
                    .getProjectController()
                    .addPresentationTab(newPresentation, projectNode.getColor());

        } else if (selected instanceof Presentation presNode) {
            Slide newSlide = new Slide(presNode, "New Slide", "Empty");
            presNode.addChild(newSlide);
            graffTree.addChild(presNode, newSlide);
        }
    }
}
