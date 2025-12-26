package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.gui.swing.SlideView;
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

        if (clickedElement != null) {
            slideView.getSlide().removeElement(clickedElement);
            slideView.getSelectedElements().remove(clickedElement);
            slideView.repaint();
        } else if (!slideView.getSelectedElements().isEmpty()) {
            List<SlideElement> toDelete = new ArrayList<>(slideView.getSelectedElements());
            for (SlideElement element : toDelete) {
                slideView.getSlide().removeElement(element);
            }
            slideView.getSelectedElements().clear();
            slideView.repaint();
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
