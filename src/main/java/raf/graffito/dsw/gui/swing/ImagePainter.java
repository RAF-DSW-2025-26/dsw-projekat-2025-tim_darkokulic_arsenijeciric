package raf.graffito.dsw.gui.swing.view;

import raf.graffito.dsw.model.ImageElement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImagePainter {

    private ImageElement image;

    public ImagePainter(ImageElement image) {
        this.image = image;
    }

    public void paint(Graphics2D g) {
        try {
            Image img = ImageIO.read(new File(image.getImagePath()));

            g.translate(image.getPosition().x, image.getPosition().y);
            g.rotate(image.getRotation());

            g.drawImage(
                    img,
                    0,
                    0,
                    image.getSize().width,
                    image.getSize().height,
                    null
            );

            g.rotate(-image.getRotation());
            g.translate(-image.getPosition().x, -image.getPosition().y);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
