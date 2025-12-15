package raf.graffito.dsw.gui.swing.view;

import raf.graffito.dsw.model.LogoElement;

import java.awt.*;

public class LogoPainter {

    private LogoElement logo;

    public LogoPainter(LogoElement logo) {
        this.logo = logo;
    }

    public void paint(Graphics2D g) {
        g.translate(logo.getPosition().x, logo.getPosition().y);
        g.rotate(logo.getRotation());

        g.setColor(Color.BLUE);

        int w = logo.getSize().width;
        int h = logo.getSize().height;

        Polygon shape = new Polygon();
        shape.addPoint(0, h);
        shape.addPoint(w / 2, 0);
        shape.addPoint(w, h);

        g.fillPolygon(shape);

        g.rotate(-logo.getRotation());
        g.translate(-logo.getPosition().x, -logo.getPosition().y);
    }
}
