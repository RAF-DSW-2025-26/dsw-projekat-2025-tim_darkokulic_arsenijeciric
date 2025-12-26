package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.SlideElement;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ResizeState implements State {

    private Point resizeStart;
    private Dimension originalSize;
    private Point originalPosition;
    private SlideElement resizingElement;

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
        Point clickPoint = new Point(
                (int) (e.getX() / slideView.getZoomFactor()),
                (int) (e.getY() / slideView.getZoomFactor())
        );

        if (!slideView.getSelectedElements().isEmpty()) {
            resizingElement = slideView.getSelectedElements().get(0);
            resizeStart = clickPoint;
            originalSize = new Dimension(resizingElement.getSize());
            originalPosition = new Point(resizingElement.getPosition());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
        resizingElement = null;
        resizeStart = null;
        originalSize = null;
        originalPosition = null;
    }

    @Override
    public void mouseClicked(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {
        if (resizingElement == null || resizeStart == null) return;

        Point currentPoint = new Point(
                (int) (e.getX() / slideView.getZoomFactor()),
                (int) (e.getY() / slideView.getZoomFactor())
        );

        int deltaX = currentPoint.x - resizeStart.x;
        int deltaY = currentPoint.y - resizeStart.y;

        int newWidth = Math.max(10, originalSize.width + deltaX);
        int newHeight = Math.max(10, originalSize.height + deltaY);

        // cuva aspect ratio ako je shift pritisnut
        if (e.isShiftDown()) {
            double aspectRatio = (double) originalSize.width / originalSize.height;
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                newHeight = (int) (newWidth / aspectRatio);
            } else {
                newWidth = (int) (newHeight * aspectRatio);
            }
        }

        resizingElement.setSize(new Dimension(newWidth, newHeight));
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
        return "Resize";
    }
}
