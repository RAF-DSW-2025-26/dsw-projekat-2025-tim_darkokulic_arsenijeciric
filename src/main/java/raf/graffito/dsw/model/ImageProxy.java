package raf.graffito.dsw.model;

import java.awt.image.BufferedImage;

public interface ImageProxy {
    BufferedImage getImage();
    BufferedImage getThumbnail();
    String getPath();
    boolean isLoaded();
}

