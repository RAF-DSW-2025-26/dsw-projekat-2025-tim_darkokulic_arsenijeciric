package raf.graffito.dsw.gui.swing.painter;

import raf.graffito.dsw.model.LogoElement;

import java.awt.*;
import java.awt.geom.Arc2D;

public class LogoPainter {

    private LogoElement logo;

    public LogoPainter(LogoElement logo) {
        this.logo = logo;
    }

    public void paint(Graphics2D g) {
        int w = logo.getSize().width;
        int h = logo.getSize().height;

        int elementCenterX = logo.getPosition().x + w / 2;
        int elementCenterY = logo.getPosition().y + h / 2;

        g.translate(elementCenterX, elementCenterY);
        g.rotate(logo.getRotation());

        g.translate(-w / 2f, -h / 2f);

        float scale = Math.min(w / 100f, h / 100f);

        float centerX = w / 2f;
        float centerY = h / 2.5f;

        float outerBlueRadius = 18 * scale;
        float yellowRadius = 13 * scale;
        float redRadius = 9 * scale;

        g.setColor(new Color(100, 149, 237));
        g.fillOval(
            (int)(centerX - outerBlueRadius),
            (int)(centerY - outerBlueRadius),
            (int)(outerBlueRadius * 2),
            (int)(outerBlueRadius * 2)
        );

        g.setColor(new Color(255, 215, 0));
        g.fillOval(
            (int)(centerX - yellowRadius),
            (int)(centerY - yellowRadius),
            (int)(yellowRadius * 2),
            (int)(yellowRadius * 2)
        );

        g.setColor(new Color(220, 20, 60));
        g.fillOval(
            (int)(centerX - redRadius),
            (int)(centerY - redRadius + 5),
            (int)(redRadius * 2),
            (int)(redRadius * 2)
        );

        g.setColor(new Color(100, 149, 237));
        g.setStroke(new BasicStroke(5 * scale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        float dashStartY = centerY + outerBlueRadius ;
        float dashLength = 15 * scale;

        g.drawLine(
            (int)centerX,
            (int)dashStartY,
            (int)centerX,
            (int)(dashStartY + dashLength + 20)
        );

        float leftDashX = centerX - 18 * scale;
        g.drawLine(
            (int)(leftDashX + 2 * scale + 4),
                (int)(dashStartY + 10),
            (int)(leftDashX - 2 * scale + 4),
            (int)(dashStartY + dashLength + 5)
        );

        float rightDashX = centerX + 18 * scale;
        g.drawLine(
            (int)(rightDashX - 2 * scale - 5),
                (int)(dashStartY + 10),
            (int)(rightDashX + 2 * scale - 5),
            (int)(dashStartY + dashLength + 10)
        );

        g.setStroke(new BasicStroke(4.3f * scale, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));

        Arc2D arc = new Arc2D.Float(
            centerX - outerBlueRadius - 10 * scale,
            centerY - 25 * scale,
            54 * scale,
            50 * scale,
            50,
            220,
            Arc2D.OPEN
        );
        g.draw(arc);

        // Reverse transformations
        g.translate(w / 2f, h / 2f);
        g.rotate(-logo.getRotation());
        g.translate(-elementCenterX, -elementCenterY);
    }
}
