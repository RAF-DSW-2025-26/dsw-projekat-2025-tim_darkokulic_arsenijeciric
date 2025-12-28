package raf.graffito.dsw.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RealImage implements ImageProxy {
    
    private final String path;
    private BufferedImage image;
    private BufferedImage thumbnail;
    
    public RealImage(String path) {
        this.path = path;
        loadImage();
    }
    
    private RealImage(String path, BufferedImage image) {
        this.path = path;
        this.image = image;
        if (image != null) {
            this.thumbnail = createThumbnail(image);
        }
    }
    
    public static RealImage fromBufferedImage(String path, BufferedImage image) {
        return new RealImage(path, image);
    }
    
    private void loadImage() {
        try {
            image = ImageIO.read(new File(path));
            if (image != null) {
                thumbnail = createThumbnail(image);
            } else {
                image = createPlaceholder();
                thumbnail = image;
            }
        } catch (IOException e) {
            System.err.println("Failed to load image: " + path);
            image = createPlaceholder();
            thumbnail = image;
        }
    }
    
    private BufferedImage createThumbnail(BufferedImage img) {
        Image scaled = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        BufferedImage thumb = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = thumb.createGraphics();
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();
        return thumb;
    }
    
    private BufferedImage createPlaceholder() {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, 100, 100);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("Error", 30, 50);
        g2d.dispose();
        return img;
    }
    
    @Override
    public BufferedImage getImage() {
        return image;
    }
    
    @Override
    public BufferedImage getThumbnail() {
        return thumbnail;
    }
    
    @Override
    public String getPath() {
        return path;
    }
    
    @Override
    public boolean isLoaded() {
        return image != null;
    }
}

