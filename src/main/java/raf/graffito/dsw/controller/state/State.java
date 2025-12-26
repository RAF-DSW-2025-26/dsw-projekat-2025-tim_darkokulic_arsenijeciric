package raf.graffito.dsw.controller.state;

import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.event.MouseEvent;

public interface State {

    void mousePressed(MouseEvent e, SlideView slideView);

    void mouseReleased(MouseEvent e, SlideView slideView);

    void mouseClicked(MouseEvent e, SlideView slideView);

    void mouseDragged(MouseEvent e, SlideView slideView);

    void mouseMoved(MouseEvent e, SlideView slideView);

    void mouseWheelMoved(int rotation, MouseEvent e, SlideView slideView);

    String getStateName();
}
