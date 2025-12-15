package raf.graffito.dsw.gui.swing.view;

import raf.graffito.dsw.model.LabelElement;

import java.awt.*;

public class LabelPainter {

    private LabelElement label;

    public LabelPainter(LabelElement label) {
        this.label = label;
    }

    public void paint(Graphics2D g) {
        g.translate(label.getPosition().x, label.getPosition().y);
        g.rotate(label.getRotation());

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString(label.getText(), 0, 0);

        g.rotate(-label.getRotation());
        g.translate(-label.getPosition().x, -label.getPosition().y);
    }
}
