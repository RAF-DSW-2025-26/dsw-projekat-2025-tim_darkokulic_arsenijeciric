package raf.graffito.dsw.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.awt.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ImageElement.class, name = "IMAGE"),
    @JsonSubTypes.Type(value = LabelElement.class, name = "LABEL"),
    @JsonSubTypes.Type(value = LogoElement.class, name = "LOGO")
})
public abstract class SlideElement {

    protected Point position;
    protected Dimension size;
    protected double rotation;

    public SlideElement(Point position, Dimension size, double rotation) {
        this.position = position;
        this.size = size;
        this.rotation = rotation;
    }

    public abstract SlideElement copy();

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
