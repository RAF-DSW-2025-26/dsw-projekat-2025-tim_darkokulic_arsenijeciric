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
    private SlideController slideController;

    // State actions
    private SetStateAction selectStateAction;
    private SetStateAction moveStateAction;
    private SetStateAction resizeStateAction;
    private SetStateAction deleteStateAction;
    private SetStateAction zoomStateAction;

    // Add element actions
    private SetAddTypeAction addTextAction;
    private SetAddTypeAction addImageAction;
    private SetAddTypeAction addLogoAction;

    // Rotate actions
    private RotateLeftAction rotateLeftAction;
    private RotateRightAction rotateRightAction;

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
        slideController = new SlideController(mainFrame.getPresentationView());

        selectStateAction = new SetStateAction("select", "Select", "Select elements");
        moveStateAction = new SetStateAction("move", "Move", "Move selected elements");
        resizeStateAction = new SetStateAction("resize", "Resize", "Resize selected elements");
        deleteStateAction = new SetStateAction("delete", "Delete Element", "Delete elements by clicking on them");
        zoomStateAction = new SetStateAction("zoom", "Zoom", "Zoom with mouse wheel");

        addTextAction = new SetAddTypeAction("text", "Add Text");
        addImageAction = new SetAddTypeAction("image", "Add Image");
        addLogoAction = new SetAddTypeAction("logo", "Add Logo");

        rotateLeftAction = new RotateLeftAction();
        rotateRightAction = new RotateRightAction();

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

    public SlideController getSlideController() {
        return slideController;
    }

    public SetStateAction getSelectStateAction() {
        return selectStateAction;
    }

    public SetStateAction getMoveStateAction() {
        return moveStateAction;
    }

    public SetStateAction getResizeStateAction() {
        return resizeStateAction;
    }

    public SetStateAction getDeleteStateAction() {
        return deleteStateAction;
    }

    public SetStateAction getZoomStateAction() {
        return zoomStateAction;
    }

    public SetAddTypeAction getAddTextAction() {
        return addTextAction;
    }

    public SetAddTypeAction getAddImageAction() {
        return addImageAction;
    }

    public SetAddTypeAction getAddLogoAction() {
        return addLogoAction;
    }

    public RotateLeftAction getRotateLeftAction() {
        return rotateLeftAction;
    }

    public RotateRightAction getRotateRightAction() {
        return rotateRightAction;
    }

}