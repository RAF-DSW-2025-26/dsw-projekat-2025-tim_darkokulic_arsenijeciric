package raf.graffito.dsw.model;

import java.awt.*;

public class LabelElement extends SlideElement {

    private String text;

    public LabelElement(Point position, Dimension size, double rotation, String text) {
        super(position, size, rotation);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public SlideElement copy() {
        return new LabelElement(
                new Point(position),
                new Dimension(size),
                rotation,
                text
        );
    }

    @Override
    public String toString() {
        String preview = text.length() > 20 ? text.substring(0, 20) + "..." : text;
        return "Text: " + preview;
    }
}
