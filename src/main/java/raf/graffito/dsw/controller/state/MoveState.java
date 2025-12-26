package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.SlideElement;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MoveState implements State {

    private Point lastMousePosition;
    private boolean isDragging = false;

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
        Point clickPoint = new Point(
                (int) (e.getX() / slideView.getZoomFactor()),
                (int) (e.getY() / slideView.getZoomFactor())
        );

        lastMousePosition = clickPoint;
        isDragging = true;
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
        isDragging = false;
        lastMousePosition = null;
    }

    @Override
    public void mouseClicked(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {
        if (!isDragging || lastMousePosition == null) return;

        Point currentPoint = new Point(
                (int) (e.getX() / slideView.getZoomFactor()),
                (int) (e.getY() / slideView.getZoomFactor())
        );

        int deltaX = currentPoint.x - lastMousePosition.x;
        int deltaY = currentPoint.y - lastMousePosition.y;

        for (SlideElement element : slideView.getSelectedElements()) {
            Point newPosition = new Point(
                    element.getPosition().x + deltaX,
                    element.getPosition().y + deltaY
            );
            element.setPosition(newPosition);
        }

        lastMousePosition = currentPoint;
        slideView.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseWheelMoved(int rotation, MouseEvent e, SlideView slideView) {
    }

    @Override
    public String getStateName() {
        return "Move";
    }
}
