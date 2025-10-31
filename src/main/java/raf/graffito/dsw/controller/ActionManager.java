package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.GraffTree;

import javax.swing.*;

public class ActionManager {
    private ExitAction exitAction;
    private AboutUsAction aboutAction;
    private AddAction addAction;
    private RenameAction renameAction;
    private DeleteAction deleteAction;
    private EditProjectAction editProjectAction;

    public ActionManager(GraffTree graffTree, JFrame parent) {
        exitAction = new ExitAction();
        aboutAction = new AboutUsAction();
        addAction = new AddAction(graffTree);
        renameAction = new RenameAction(graffTree, parent);
        deleteAction = new DeleteAction(graffTree);
        editProjectAction = new EditProjectAction(graffTree, parent);
        editProjectAction.setEnabled(false);
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
}
