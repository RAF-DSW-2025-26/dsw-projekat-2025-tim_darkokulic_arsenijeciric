package raf.graffito.dsw.controller;

import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.controller.state.StateManager;
import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.gui.swing.ProjectOpenDialog;
import raf.graffito.dsw.gui.swing.jtree.GraffTree;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;
import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.repository.GraffRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddAction extends AbstractGraffAction{

    private GraffTree graffTree;
    private ProjectTabController projectController;

    public AddAction(GraffTree graffTree, ProjectTabController projectController) {
        this.graffTree = graffTree;
        this.projectController = projectController;

        putValue(NAME, "Add");
        putValue(SHORT_DESCRIPTION, "Add a new element");
        Icon icon = loadIcon("/images/add.png");
        putValue(SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffNode selected = graffTree.getSelectedNode();
        if (selected == null) return;

        GraffRepository repository = ApplicationFramework.getInstance().getRepository();
        GraffNode newNode = null;

        if (selected instanceof Workspace workspace) {
            // pokaze dijalog da se izabere pocetni content
            ProjectOpenDialog dialog = new ProjectOpenDialog(MainFrame.getInstance());
            dialog.setVisible(true);

            if (!dialog.isConfirmed()) {
                return;
            }

            // napravi projekat
            newNode = repository.createNode(workspace, Project.class, "New Project", "Unknown");

            if (newNode instanceof Project newProject) {
                // dodaje projekat u JTree
                graffTree.addChild(selected, newNode);

                // onda dodaje sadrzaj na osnovu izbora
                String choice = dialog.getChoice();

                if ("presentation".equals(choice)) {
                    GraffNode presentation = repository.createNode(newProject, Presentation.class, "New Presentation");
                    if (presentation != null) {
                        graffTree.addChild(newProject, presentation);
                    }
                } else if ("slide".equals(choice)) {
                    GraffNode slide = repository.createNode(newProject, Slide.class, "New Slide", "Empty");
                    if (slide != null) {
                        graffTree.addChild(newProject, slide);
                    }
                }

                // otvara projekat sa defaul bojama
                ColorDecorator defaultDecorator = new ColorDecorator(newProject, Color.WHITE);
                repository.setProjectDecorator(newProject, defaultDecorator);

                MainFrame.getInstance()
                        .getActionManager()
                        .getProjectController()
                        .openProjectTabsNoColor(newProject, defaultDecorator);
            }

        } else if (selected instanceof Project project) {
            PresentationView presentationView = MainFrame.getInstance().getPresentationView();

            if (!presentationView.isProjectOpen(project)) {
                ColorDecorator decorator = repository.getProjectDecorator(project);

                if (decorator == null) {
                    decorator = new ColorDecorator(project, Color.LIGHT_GRAY);
                    repository.setProjectDecorator(project, decorator);
                }

                MainFrame.getInstance()
                        .getActionManager()
                        .getProjectController()
                        .openProjectTabsNoColor(project, decorator);
            }

            newNode = repository.createNode(project, Presentation.class, "New Presentation");

            if (newNode instanceof Presentation presentation) {
                ColorDecorator decorator = repository.getProjectDecorator(project);

                if (decorator != null) {
                    Color projectColor = decorator.getColor();

                    MainFrame.getInstance()
                            .getActionManager()
                            .getProjectController()
                            .addPresentationTab(presentation, projectColor);
                }
            }

        } else if (selected instanceof Presentation presentation) {
            newNode = repository.createNode(presentation, Slide.class, "New Slide", "Empty");
        }

        if (newNode != null && !(selected instanceof Workspace)) {
            graffTree.addChild(selected, newNode);
        }
    }
}