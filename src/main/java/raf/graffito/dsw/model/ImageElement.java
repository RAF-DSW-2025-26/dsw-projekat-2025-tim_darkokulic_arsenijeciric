package raf.graffito.dsw.model;

import java.awt.*;

public class ImageElement extends SlideElement {

    private String imagePath;

    public ImageElement(Point position, Dimension size, double rotation, String imagePath) {
        super(position, size, rotation);
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public SlideElement copy() {
        return new ImageElement(
                new Point(position),
                new Dimension(size),
                rotation,
                imagePath
        );
    }
}
