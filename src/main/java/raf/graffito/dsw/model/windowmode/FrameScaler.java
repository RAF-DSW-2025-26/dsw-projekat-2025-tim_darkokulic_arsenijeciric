package raf.graffito.dsw.model.windowmode;

import raf.graffito.dsw.gui.swing.MainFrame;

import javax.swing.*;
import java.awt.*;

public class FrameScaler implements WindowScaler {

    private final MainFrame mainFrame;

    public FrameScaler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void applyScaling(Dimension targetDimension, double scaleFactor, boolean isFullscreen) {
        if (isFullscreen) {
            mainFrame.dispose();
            mainFrame.setUndecorated(true);
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainFrame.setVisible(true);
        } else {
            if (mainFrame.isUndecorated()) {
                mainFrame.dispose();
                mainFrame.setUndecorated(false);
                mainFrame.setVisible(true);
            }
            mainFrame.setExtendedState(JFrame.NORMAL);
            mainFrame.setSize(targetDimension);
            mainFrame.setLocationRelativeTo(null);
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
