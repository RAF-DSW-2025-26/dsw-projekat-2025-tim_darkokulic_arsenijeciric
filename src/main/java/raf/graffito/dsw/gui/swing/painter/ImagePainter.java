package raf.graffito.dsw.gui.swing.painter;

import raf.graffito.dsw.model.ImageElement;

import java.awt.*;

public class ImagePainter {

    private ImageElement imageElement;

    public ImagePainter(ImageElement imageElement) {
        this.imageElement = imageElement;
    }

    public void paint(Graphics2D g) {
        if (imageElement.getImage() == null) return;

        int centerX = imageElement.getPosition().x + imageElement.getSize().width / 2;
        int centerY = imageElement.getPosition().y + imageElement.getSize().height / 2;

        g.translate(centerX, centerY);
        g.rotate(imageElement.getRotation());

        g.drawImage(
                imageElement.getImage(),
                -imageElement.getSize().width / 2,
                -imageElement.getSize().height / 2,
                imageElement.getSize().width,
                imageElement.getSize().height,
                null
        );

        g.rotate(-imageElement.getRotation());
        g.translate(-centerX, -centerY);
    }
}
