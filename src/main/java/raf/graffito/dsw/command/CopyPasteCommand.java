package raf.graffito.dsw.command;

import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.SlideElement;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class CopyPasteCommand implements Command {
    
    private final Slide slide;
    private final List<SlideElement> originalElements;
    private final List<SlideElement> pastedElements;
    private final Point pasteOffset;
    
    public CopyPasteCommand(Slide slide, List<SlideElement> elementsToCopy, Point pasteOffset) {
        this.slide = slide;
        this.originalElements = new ArrayList<>(elementsToCopy);
        this.pastedElements = new ArrayList<>();
        this.pasteOffset = new Point(pasteOffset);
        createCopies();
    }
    
    private void createCopies() {
        for (SlideElement original : originalElements) {
            SlideElement copy = original.copy();
            Point newPosition = new Point(
                copy.getPosition().x + pasteOffset.x,
                copy.getPosition().y + pasteOffset.y
            );
            copy.setPosition(newPosition);
            pastedElements.add(copy);
        }
    }
    
    @Override
    public void execute() {
        for (SlideElement element : pastedElements) {
            slide.addElement(element);
        }
    }
    
    @Override
    public void undo() {
        for (SlideElement element : pastedElements) {
            slide.removeElement(element);
        }
    }
}

