package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.event.MouseEvent;

public class ZoomState implements State {

    private static final double MIN_ZOOM = 0.25;
    private static final double MAX_ZOOM = 4.0;
    private static final double ZOOM_STEP = 0.1;

    @Override
    public void mouseWheelMoved(int rotation, MouseEvent e, SlideView slideView) {
        double currentZoom = slideView.getZoomFactor();
        double newZoom = currentZoom;

        if (rotation < 0) {
            newZoom = Math.min(MAX_ZOOM, currentZoom + ZOOM_STEP);
        } else {
            newZoom = Math.max(MIN_ZOOM, currentZoom - ZOOM_STEP);
        }

        slideView.setZoomFactor(newZoom);
        slideView.revalidate();
        slideView.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseClicked(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {
    }

    @Override
    public void mouseMoved(MouseEvent e, SlideView slideView) {
    }

    @Override
    public String getStateName() {
        return "Zoom";
    }
}
