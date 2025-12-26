package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.SlideElement;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class SelectState implements State {

    private Point selectionStart;
    private Point selectionEnd;
    private boolean isDrawingSelection = false;

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
        Point clickPoint = new Point(
                (int) (e.getX() / slideView.getZoomFactor()),
                (int) (e.getY() / slideView.getZoomFactor())
        );

        SlideElement clickedElement = findElementAtPoint(clickPoint, slideView);

        if (clickedElement != null) {

            if (e.isShiftDown()) {
                if (slideView.getSelectedElements().contains(clickedElement)) {
                    slideView.getSelectedElements().remove(clickedElement);
                } else {
                    slideView.getSelectedElements().add(clickedElement);
                }
            } else {
                slideView.getSelectedElements().clear();
                slideView.getSelectedElements().add(clickedElement);
            }
        } else {

            if (!e.isShiftDown()) {
                slideView.getSelectedElements().clear();
            }
            selectionStart = clickPoint;
            isDrawingSelection = true;
        }

        slideView.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
        if (isDrawingSelection && selectionStart != null && selectionEnd != null) {

            Rectangle selectionRect = createRectangle(selectionStart, selectionEnd);

            for (SlideElement element : slideView.getSlide().getElements()) {
                Rectangle elementBounds = new Rectangle(
                        element.getPosition(),
                        element.getSize()
                );

                if (selectionRect.intersects(elementBounds)) {
                    if (!slideView.getSelectedElements().contains(element)) {
                        slideView.getSelectedElements().add(element);
                    }
                }
            }
        }

        isDrawingSelection = false;
        selectionStart = null;
        selectionEnd = null;
        slideView.setSelectionRectangle(null);
        slideView.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {
        if (isDrawingSelection && selectionStart != null) {
            selectionEnd = new Point(
                    (int) (e.getX() / slideView.getZoomFactor()),
                    (int) (e.getY() / slideView.getZoomFactor())
            );

            Rectangle selectionRect = createRectangle(selectionStart, selectionEnd);
            slideView.setSelectionRectangle(selectionRect);
            slideView.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseWheelMoved(int rotation, MouseEvent e, SlideView slideView) {
    }

    @Override
    public String getStateName() {
        return "Select";
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

    private Rectangle createRectangle(Point p1, Point p2) {
        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int width = Math.abs(p1.x - p2.x);
        int height = Math.abs(p1.y - p2.y);
        return new Rectangle(x, y, width, height);
    }
}
