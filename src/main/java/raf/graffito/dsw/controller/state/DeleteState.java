package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.command.DeleteElementCommand;
import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.SlideElement;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DeleteState implements State {

    @Override
    public void mouseClicked(MouseEvent e, SlideView slideView) {
        Point clickPoint = new Point(
                (int) (e.getX() / slideView.getZoomFactor()),
                (int) (e.getY() / slideView.getZoomFactor())
        );

        SlideElement clickedElement = findElementAtPoint(clickPoint, slideView);
        Slide slide = slideView.getSlide();
        UndoRedoManager manager = slideView.getUndoRedoManager();

        if (clickedElement != null) {
            DeleteElementCommand deleteCommand = new DeleteElementCommand(slide, clickedElement);
            manager.execute(deleteCommand);
            slideView.getSelectedElements().remove(clickedElement);
            slideView.repaint();
            updateButtonStates();
        } else if (!slideView.getSelectedElements().isEmpty()) {
            List<SlideElement> toDelete = new ArrayList<>(slideView.getSelectedElements());
            DeleteElementCommand deleteCommand = new DeleteElementCommand(slide, toDelete);
            manager.execute(deleteCommand);
            slideView.getSelectedElements().clear();
            slideView.repaint();
            updateButtonStates();
        }
    }
    
    private void updateButtonStates() {
        MainFrame mainFrame = MainFrame.getInstance();
        if (mainFrame != null && mainFrame.getActionManager() != null) {
            mainFrame.getActionManager().updateUndoRedoButtonStates();
        }
    }

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseMoved(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseWheelMoved(int rotation, MouseEvent e, SlideView slideView) {
    }

    @Override
    public String getStateName() {
        return "Delete Element";
    }

    private SlideElement findElementAtPoint(Point point, SlideView slideView) {
        java.util.List<SlideElement> elements = slideView.getSlide().getElements();
        for (int i = elements.size() - 1; i >= 0; i--) {
            SlideElement element = elements.get(i);
            Rectangle bounds = new Rectangle(element.getPosition(), element.getSize());

            if (bounds.contains(point)) {
                return element;
            }
        }
        return null;
    }
}
