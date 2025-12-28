package raf.graffito.dsw.command;

import raf.graffito.dsw.model.SlideElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RotateElementCommand implements Command {
    
    private final List<SlideElement> elements;
    private final double rotationDelta;
    private final Map<SlideElement, Double> previousRotations;
    
    public RotateElementCommand(SlideElement element, double rotationDelta) {
        this.elements = new ArrayList<>();
        this.elements.add(element);
        this.rotationDelta = rotationDelta;
        this.previousRotations = new HashMap<>();
        storePreviousRotations();
    }
    
    public RotateElementCommand(List<SlideElement> elements, double rotationDelta) {
        this.elements = new ArrayList<>(elements);
        this.rotationDelta = rotationDelta;
        this.previousRotations = new HashMap<>();
        storePreviousRotations();
    }
    
    private void storePreviousRotations() {
        for (SlideElement element : elements) {
            previousRotations.put(element, element.getRotation());
        }
    }
    
    @Override
    public void execute() {
        for (SlideElement element : elements) {
            double newRotation = element.getRotation() + rotationDelta;
            element.setRotation(newRotation);
        }
    }
    
    @Override
    public void undo() {
        for (SlideElement element : elements) {
            Double previousRotation = previousRotations.get(element);
            if (previousRotation != null) {
                element.setRotation(previousRotation);
            }
        }
    }
}
