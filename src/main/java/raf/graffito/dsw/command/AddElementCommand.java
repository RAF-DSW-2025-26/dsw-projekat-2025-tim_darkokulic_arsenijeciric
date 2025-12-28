package raf.graffito.dsw.command;

import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.SlideElement;

public class AddElementCommand implements Command {
    
    private final Slide slide;
    private final SlideElement element;
    
    public AddElementCommand(Slide slide, SlideElement element) {
        this.slide = slide;
        this.element = element;
    }
    
    @Override
    public void execute() {
        slide.addElement(element);
    }
    
    @Override
    public void undo() {
        slide.removeElement(element);
    }
}

