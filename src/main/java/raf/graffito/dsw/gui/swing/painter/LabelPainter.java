package raf.graffito.dsw.gui.swing.painter;

import raf.graffito.dsw.model.LabelElement;

import java.awt.*;

public class LabelPainter {

    private LabelElement label;

    public LabelPainter(LabelElement label) {
        this.label = label;
    }

    public void paint(Graphics2D g) {
        int centerX = label.getPosition().x + label.getSize().width / 2;
        int centerY = label.getPosition().y + label.getSize().height / 2;

        g.translate(centerX, centerY);
        g.rotate(label.getRotation());

        int fontSize = Math.max(10, Math.min(label.getSize().height - 10, 72));
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(label.getText());
        int textHeight = fm.getAscent();

        int x = -textWidth / 2;
        int y = textHeight / 2 - fm.getDescent();

        g.setColor(Color.BLACK);
        g.drawString(label.getText(), x, y);

        g.rotate(-label.getRotation());
        g.translate(-centerX, -centerY);
    }
}
