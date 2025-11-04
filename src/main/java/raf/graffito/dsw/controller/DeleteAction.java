package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.GraffTree;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteAction extends AbstractGraffAction {

    private GraffTree graffTree;
    private GraffRepository repository;

    public DeleteAction(GraffTree graffTree, GraffRepository repository) {
        this.graffTree = graffTree;
        this.repository = repository;

        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete selected node");
        Icon icon = loadIcon("/images/delete.png");
        putValue(SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffNode selected = graffTree.getSelectedNode();
        if (selected == null) return;

        if (selected instanceof Presentation presentation) {
            MainFrame.getInstance().getPresentationView().closePresentationTab(presentation);
        }

        if (repository.removeNode(selected)) {
            graffTree.removeNode(selected);
        }
    }
}