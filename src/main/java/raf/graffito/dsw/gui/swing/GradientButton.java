package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GradientButton extends JButton {

    private Color topColor;
    private Color bottomColor;
    private boolean isHovered = false;

    public GradientButton(String text, Color topColor, Color bottomColor) {
        super(text);
        this.topColor = topColor;
        this.bottomColor = bottomColor;

        setForeground(Color.BLACK);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color top = topColor;
        Color bottom = bottomColor;

        GradientPaint gradient = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

        if (isHovered) {
            g2d.setStroke(new BasicStroke(2.5f));
            g2d.setColor(new Color(97, 97, 97));
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        } else {
            g2d.setStroke(new BasicStroke(1f));
            g2d.setColor(bottomColor.darker());
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        }

        g2d.dispose();
        super.paintComponent(g);
    }
}
