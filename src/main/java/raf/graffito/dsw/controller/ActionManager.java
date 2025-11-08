package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.jtree.GraffTree;
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
    private HelpAction helpAction;

    public ActionManager(GraffTree graffTree, JFrame parent, MainFrame mainFrame, Workspace workspace) {

        exitAction = new ExitAction();
        aboutAction = new AboutUsAction();
        addAction = new AddAction(graffTree, projectController);
        renameAction = new RenameAction(graffTree, parent);
        deleteAction = new DeleteAction(graffTree);
        helpAction = new HelpAction();
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

    public HelpAction getHelpAction() { return this.helpAction; }

}