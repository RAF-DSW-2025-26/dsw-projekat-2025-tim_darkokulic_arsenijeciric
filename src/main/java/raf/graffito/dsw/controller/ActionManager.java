package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.GraffTree;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.*;

import javax.swing.*;

public class ActionManager {
    private ExitAction exitAction;
    private AboutUsAction aboutAction;
    private AddAction addAction;
    private RenameAction renameAction;
    private DeleteAction deleteAction;
    private EditProjectAction editProjectAction;
    private ProjectTabController projectController;
    private GraffRepository repository;

    public ActionManager(GraffTree graffTree, JFrame parent, MainFrame mainFrame, Workspace workspace) {

        repository = new GraffRepository(workspace);

        exitAction = new ExitAction();
        aboutAction = new AboutUsAction();
        addAction = new AddAction(graffTree, repository);
        renameAction = new RenameAction(graffTree, parent, repository);  // Pass repository here
        deleteAction = new DeleteAction(graffTree, repository);
        editProjectAction = new EditProjectAction(graffTree, parent);
        editProjectAction.setEnabled(false);
        projectController = new ProjectTabController(mainFrame, mainFrame.getPresentationView());

    }

    public ExitAction getExitAction(){
        return exitAction;
    }

    public AboutUsAction getAboutUsAction(){
        return aboutAction;
    }

    public AddAction getAddAction() {
        return addAction;
    }

    public RenameAction getRenameAction() {
        return renameAction;
    }

    public DeleteAction getDeleteAction() {
        return deleteAction;
    }

    public EditProjectAction getEditProjectAction() {
        return editProjectAction;
    }

    public ProjectTabController getProjectController() {
        return projectController;
    }

    public GraffRepository getRepository() {
        return repository;
    }
}