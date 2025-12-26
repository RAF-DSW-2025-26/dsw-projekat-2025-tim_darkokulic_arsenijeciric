package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.event.MouseEvent;

public class StateManager {

    private State currentState;
    private AddState addState;
    private SelectState selectState;
    private ResizeState resizeState;
    private MoveState moveState;
    private ZoomState zoomState;
    private DeleteState deleteState;

    public StateManager() {
        this.addState = new AddState();
        this.selectState = new SelectState();
        this.resizeState = new ResizeState();
        this.moveState = new MoveState();
        this.zoomState = new ZoomState();
        this.deleteState = new DeleteState();

        // default je select state
        this.currentState = selectState;
    }

    public void setAddState() {
        this.currentState = addState;
    }

    public void setSelectState() {
        this.currentState = selectState;
    }

    public void setResizeState() {
        this.currentState = resizeState;
    }

    public void setMoveState() {
        this.currentState = moveState;
    }

    public void setZoomState() {
        this.currentState = zoomState;
    }

    public void setDeleteState() {
        this.currentState = deleteState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void mousePressed(MouseEvent e, SlideView slideView) {
        currentState.mousePressed(e, slideView);
    }

    public void mouseReleased(MouseEvent e, SlideView slideView) {
        currentState.mouseReleased(e, slideView);
    }

    public void mouseClicked(MouseEvent e, SlideView slideView) {
        currentState.mouseClicked(e, slideView);
    }

    public void mouseDragged(MouseEvent e, SlideView slideView) {
        currentState.mouseDragged(e, slideView);
    }

    public void mouseMoved(MouseEvent e, SlideView slideView) {
        currentState.mouseMoved(e, slideView);
    }

    public void mouseWheelMoved(int rotation, MouseEvent e, SlideView slideView) {
        currentState.mouseWheelMoved(rotation, e, slideView);
    }

    public AddState getAddState() {
        return addState;
    }
}
