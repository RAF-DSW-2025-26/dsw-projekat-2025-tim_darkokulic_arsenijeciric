package raf.graffito.dsw.controller;

import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.gui.swing.jtree.GraffTree;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.windowmode.WindowModeManager;

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

    private SetStateAction selectStateAction;
    private SetStateAction moveStateAction;
    private SetStateAction resizeStateAction;
    private SetStateAction deleteStateAction;
    private SetStateAction zoomStateAction;

    private SetAddTypeAction addTextAction;
    private SetAddTypeAction addImageAction;
    private SetAddTypeAction addLogoAction;

    private RotateLeftAction rotateLeftAction;
    private RotateRightAction rotateRightAction;

    private DuplicateAction duplicateAction;

    private WindowModeAction normalModeAction;
    private WindowModeAction fullscreenModeAction;
    private WindowModeAction smallModeAction;

    public ActionManager(GraffTree graffTree, JFrame parent, MainFrame mainFrame, Workspace workspace, WindowModeManager windowModeManager) {
    // Undo/Redo actions
    private UndoAction undoAction;
    private RedoAction redoAction;

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

        duplicateAction = new DuplicateAction();

        normalModeAction = new WindowModeAction(windowModeManager, "Normal");
        fullscreenModeAction = new WindowModeAction(windowModeManager, "Fullscreen");
        smallModeAction = new WindowModeAction(windowModeManager, "Small");
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        undoAction.setEnabled(false);
        redoAction.setEnabled(false);

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

    public void updateStateButtonSelection(String activeState) {
        selectStateAction.setSelected(false);
        moveStateAction.setSelected(false);
        resizeStateAction.setSelected(false);
        deleteStateAction.setSelected(false);
        zoomStateAction.setSelected(false);
        addTextAction.setSelected(false);

        switch (activeState) {
            case "select" -> selectStateAction.setSelected(true);
            case "move" -> moveStateAction.setSelected(true);
            case "resize" -> resizeStateAction.setSelected(true);
            case "delete" -> deleteStateAction.setSelected(true);
            case "zoom" -> zoomStateAction.setSelected(true);
            case "add-text" -> addTextAction.setSelected(true);
        }
    }

    public WindowModeAction getNormalModeAction() {
        return normalModeAction;
    }

    public WindowModeAction getFullscreenModeAction() {
        return fullscreenModeAction;
    }

    public WindowModeAction getSmallModeAction() {
        return smallModeAction;
    }

    public DuplicateAction getDuplicateAction() {
        return duplicateAction;
    public UndoAction getUndoAction() {
        return undoAction;
    }

    public RedoAction getRedoAction() {
        return redoAction;
    }

    public void updateUndoRedoButtonStates() {
        SlideView currentSlideView = slideController.getCurrentSlideView();
        if (currentSlideView == null) {
            undoAction.setEnabled(false);
            redoAction.setEnabled(false);
            return;
        }

        UndoRedoManager manager = currentSlideView.getUndoRedoManager();
        undoAction.setEnabled(manager.canUndo());
        redoAction.setEnabled(manager.canRedo());
    }

}