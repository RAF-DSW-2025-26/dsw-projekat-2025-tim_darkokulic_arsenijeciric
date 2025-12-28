package raf.graffito.dsw.command;

import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.SlideElement;

import java.util.ArrayList;
import java.util.List;

public class DeleteElementCommand implements Command {
    
    private final Slide slide;
    private final List<SlideElement> elements;
    
    public DeleteElementCommand(Slide slide, SlideElement element) {
        this.slide = slide;
        this.elements = new ArrayList<>();
        this.elements.add(element);
    }
    
    public DeleteElementCommand(Slide slide, List<SlideElement> elements) {
        this.slide = slide;
        this.elements = new ArrayList<>(elements);
    }
    
    @Override
    public void execute() {
        for (SlideElement element : elements) {
            slide.removeElement(element);
        }
    }
    
    @Override
    public void undo() {
        for (int i = elements.size() - 1; i >= 0; i--) {
            SlideElement element = elements.get(i);
            slide.addElement(element);
        }
    }
}

