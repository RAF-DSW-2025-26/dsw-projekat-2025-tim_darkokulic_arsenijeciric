package raf.graffito.dsw.model;

import java.awt.image.BufferedImage;

public class ProxyImage implements ImageProxy {
    
    private final String path;
    private RealImage realImage;
    
    public ProxyImage(String path) {
        this.path = path;
    }
    
    private void loadImage() {
        if (realImage == null) {
            realImage = new RealImage(path);
        }
    }
    
    @Override
    public BufferedImage getImage() {
        loadImage();
        return realImage.getImage();
    }
    
    @Override
    public BufferedImage getThumbnail() {
        loadImage();
        return realImage.getThumbnail();
    }
    
    @Override
    public String getPath() {
        return path;
    }
    
    @Override
    public boolean isLoaded() {
        return realImage != null && realImage.isLoaded();
    }
}

