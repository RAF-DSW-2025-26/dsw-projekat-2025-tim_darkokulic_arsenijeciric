package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Project;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class ColoredTabbedPaneUI extends BasicTabbedPaneUI {

    private final PresentationView presentationView;

    public ColoredTabbedPaneUI(PresentationView presentationView) {
        this.presentationView = presentationView;
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                      int x, int y, int w, int h, boolean isSelected) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = new Color(200, 200, 200);
        Component tabComponent = tabPane.getComponentAt(tabIndex);


        if (tabComponent instanceof JPanel panel) {
            Object presObj = panel.getClientProperty("presentation");
            if (presObj instanceof Presentation pres && pres.getParent() instanceof Project project) {
                color = ApplicationFramework.getInstance().getRepository().getProjectDecorator(project).getColor();
            }
        }

        if (isSelected) {
            color = color.darker();
        }

        g2.setColor(color);

        int diagonal = 10;

        Polygon shape = new Polygon();
        shape.addPoint(x, y + h);
        shape.addPoint(x, y);
        shape.addPoint(x + w - diagonal, y);
        shape.addPoint(x + w, y + diagonal);
        shape.addPoint(x + w, y + h);

        g2.fillPolygon(shape);
        g2.dispose();
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                  int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diagonal = 10;

        Polygon shape = new Polygon();
        shape.addPoint(x, y + h);
        shape.addPoint(x, y);
        shape.addPoint(x + w - diagonal, y);
        shape.addPoint(x + w, y + diagonal);
        shape.addPoint(x + w, y + h);

        g2.setColor(Color.DARK_GRAY);
        g2.drawPolygon(shape);

        g2.dispose();
    }

    @Override
    protected int getTabRunOverlay(int tabPlacement) {
        return 0;
    }

    @Override
    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex,
                                             int x, int y, int w, int h) {
        super.paintContentBorderTopEdge(g, tabPlacement, -1, x, y, w, h);
    }

    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return 25;
    }
}
