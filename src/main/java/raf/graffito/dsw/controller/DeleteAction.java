package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.gui.swing.jtree.GraffTree;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.repository.GraffRepository;

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
        if (selected == null) return;

        GraffRepository repository = ApplicationFramework.getInstance().getRepository();

        if (selected instanceof Project project) {
            MainFrame.getInstance().getPresentationView().closeAllProjectTabs(project);
        }

        if (selected instanceof Presentation presentation) {
            MainFrame.getInstance().getPresentationView().closePresentationTab(presentation);
        }

        if (selected instanceof Slide slide && slide.getParent() instanceof Project) {
            MainFrame.getInstance().getPresentationView().closeDirectSlideTab(slide);
        }

        if (repository.removeNode(selected)) {
            graffTree.removeNode(selected);
        }
    }
}