package raf.graffito.dsw.model;

import java.awt.*;

public class LogoElement extends SlideElement {

    private String logoType; // opis / tip loga (npr. "triangle")

    public LogoElement(Point position, Dimension size, double rotation, String logoType) {
        super(position, size, rotation);
        this.logoType = logoType;
    }

    public String getLogoType() {
        return logoType;
    }

    public void setLogoType(String logoType) {
        this.logoType = logoType;
    }

    @Override
    public SlideElement copy() {
        return new LogoElement(
                new Point(position),
                new Dimension(size),
                rotation,
                logoType
        );
    }
}
