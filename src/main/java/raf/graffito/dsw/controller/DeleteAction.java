package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.GraffTree;
import raf.graffito.dsw.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteAction extends AbstractGraffAction {

    private GraffTree graffTree;

    public DeleteAction(GraffTree graffTree) {

        this.graffTree = graffTree;

        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete selected node");
        Icon icon = loadIcon("/images/delete.png");
        putValue(SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GraffNode selected = graffTree.getSelectedNode();
        if (selected == null || selected.getParent() == null) return;

        GraffNode parent = selected.getParent();
        if (parent instanceof GraffNodeComposite composite) {
            composite.removeChild(selected);
            graffTree.removeNode(selected);
        }
    }
}
