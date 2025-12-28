package raf.graffito.dsw.model.windowmode;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;
import raf.graffito.dsw.gui.swing.SlideView;

import javax.swing.*;
import java.awt.*;

public class SlideScaler implements WindowScaler {

    private final MainFrame mainFrame;

    public SlideScaler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void applyScaling(Dimension targetDimension, double scaleFactor, boolean isFullscreen) {
        PresentationView presentationView = mainFrame.getPresentationView();
        JTabbedPane tabbedPane = presentationView.getTabbedPane();

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component tabComponent = tabbedPane.getComponentAt(i);

            if (tabComponent instanceof JPanel) {
                scaleSlideViewsInPanel((JPanel) tabComponent, scaleFactor);
            }
        }

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void scaleSlideViewsInPanel(JPanel panel, double scaleFactor) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof SlideView slideView) {
                slideView.setZoomFactor(scaleFactor);
                slideView.revalidate();
                slideView.repaint();
            } else if (comp instanceof JPanel) {
                scaleSlideViewsInPanel((JPanel) comp, scaleFactor);
            } else if (comp instanceof JScrollPane scrollPane) {
                Component view = scrollPane.getViewport().getView();
                if (view instanceof JPanel) {
                    scaleSlideViewsInPanel((JPanel) view, scaleFactor);
                }
            }
        }
    }
}
