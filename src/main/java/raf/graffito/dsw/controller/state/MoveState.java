package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.command.MoveElementCommand;
import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.model.SlideElement;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveState implements State {

    private Point lastMousePosition;
    private Point startMousePosition;
    private boolean isDragging = false;
    private Map<SlideElement, Point> initialPositions;

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
        Point clickPoint = new Point(
                (int) (e.getX() / slideView.getZoomFactor()),
                (int) (e.getY() / slideView.getZoomFactor())
        );

        startMousePosition = clickPoint;
        lastMousePosition = clickPoint;
        isDragging = true;
        
        initialPositions = new HashMap<>();
        for (SlideElement element : slideView.getSelectedElements()) {
            initialPositions.put(element, new Point(element.getPosition()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
        if (isDragging && startMousePosition != null && !slideView.getSelectedElements().isEmpty()) {
            Point currentPoint = new Point(
                    (int) (e.getX() / slideView.getZoomFactor()),
                    (int) (e.getY() / slideView.getZoomFactor())
            );

            int totalDeltaX = currentPoint.x - startMousePosition.x;
            int totalDeltaY = currentPoint.y - startMousePosition.y;

            if (totalDeltaX != 0 || totalDeltaY != 0) {
                if (initialPositions != null) {
                    for (SlideElement element : slideView.getSelectedElements()) {
                        Point initialPos = initialPositions.get(element);
                        if (initialPos != null) {
                            element.setPosition(new Point(initialPos));
                        }
                    }
                }
                
                List<SlideElement> elementsToMove = new ArrayList<>(slideView.getSelectedElements());
                Point delta = new Point(totalDeltaX, totalDeltaY);
                
                MoveElementCommand moveCommand = new MoveElementCommand(elementsToMove, delta);
                UndoRedoManager manager = slideView.getUndoRedoManager();
                manager.execute(moveCommand);
                
                updateButtonStates();
            } else {
                if (initialPositions != null) {
                    for (SlideElement element : slideView.getSelectedElements()) {
                        Point initialPos = initialPositions.get(element);
                        if (initialPos != null) {
                            element.setPosition(new Point(initialPos));
                        }
                    }
                    slideView.repaint();
                }
            }
        }
        
        isDragging = false;
        lastMousePosition = null;
        startMousePosition = null;
        initialPositions = null;
    }

    @Override
    public void mouseClicked(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {
        if (!isDragging || lastMousePosition == null || initialPositions == null) return;

        Point currentPoint = new Point(
                (int) (e.getX() / slideView.getZoomFactor()),
                (int) (e.getY() / slideView.getZoomFactor())
        );

        int deltaX = currentPoint.x - lastMousePosition.x;
        int deltaY = currentPoint.y - lastMousePosition.y;

        for (SlideElement element : slideView.getSelectedElements()) {
            Point initialPos = initialPositions.get(element);
            if (initialPos != null) {
                int totalDeltaX = currentPoint.x - startMousePosition.x;
                int totalDeltaY = currentPoint.y - startMousePosition.y;
                Point newPosition = new Point(
                        initialPos.x + totalDeltaX,
                        initialPos.y + totalDeltaY
                );
                element.setPosition(newPosition);
            }
        }

        lastMousePosition = currentPoint;
        slideView.repaint();
    }
    
    private void updateButtonStates() {
        MainFrame mainFrame = MainFrame.getInstance();
        if (mainFrame != null && mainFrame.getActionManager() != null) {
            mainFrame.getActionManager().updateUndoRedoButtonStates();
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
        return "Move";
    }
}
