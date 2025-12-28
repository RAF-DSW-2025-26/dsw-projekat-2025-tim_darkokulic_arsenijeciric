package raf.graffito.dsw.command;

import raf.graffito.dsw.model.SlideElement;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ResizeElementCommand implements Command {
    
    private final List<SlideElement> elements;
    private final Dimension newSize;
    private final Map<SlideElement, Dimension> previousSizes;
   
    public ResizeElementCommand(SlideElement element, Dimension newSize) {
        this.elements = new ArrayList<>();
        this.elements.add(element);
        this.newSize = new Dimension(newSize);
        this.previousSizes = new HashMap<>();
        storePreviousSizes();
    }
    
    public ResizeElementCommand(List<SlideElement> elements, Dimension newSize) {
        this.elements = new ArrayList<>(elements);
        this.newSize = new Dimension(newSize);
        this.previousSizes = new HashMap<>();
        storePreviousSizes();
    }
   
    private void storePreviousSizes() {
        for (SlideElement element : elements) {
            previousSizes.put(element, new Dimension(element.getSize()));
        }
    }
    
    @Override
    public void execute() {
        for (SlideElement element : elements) {
            element.setSize(new Dimension(newSize));
        }
    }
    
    @Override
    public void undo() {
        for (SlideElement element : elements) {
            Dimension previousSize = previousSizes.get(element);
            if (previousSize != null) {
                element.setSize(new Dimension(previousSize));
            }
        }
    }
}

