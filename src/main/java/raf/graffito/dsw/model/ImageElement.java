package raf.graffito.dsw.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageElement extends SlideElement {

    private BufferedImage image;
    private int imageIndex;

    public ImageElement(Point position, Dimension size, double rotation, BufferedImage image, int imageIndex) {
        super(position, size, rotation);
        this.image = image;
        this.imageIndex = imageIndex;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    @Override
    public SlideElement copy() {
        return new ImageElement(
                new Point(position),
                new Dimension(size),
                rotation,
                image,
                imageIndex
        );
    }

    @Override
    public String toString() {
        return "Image #" + (imageIndex + 1);
    }
}
