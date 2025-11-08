package raf.graffito.dsw.model;

import java.awt.*;

public class ColorDecorator extends GraffNodeDecorator {

    private Color color;

    public ColorDecorator(GraffNode node, Color color) {
        super(node);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Project getProject() {
        if (decoratedNode instanceof Project) {
            return (Project) decoratedNode;
        }
        return null;
    }
}
