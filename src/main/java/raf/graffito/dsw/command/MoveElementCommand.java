package raf.graffito.dsw.command;

import raf.graffito.dsw.model.SlideElement;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MoveElementCommand implements Command {
    
    private final List<SlideElement> elements;
    private final Point delta;
    private final Map<SlideElement, Point> previousPositions;
    
    public MoveElementCommand(SlideElement element, Point delta) {
        this.elements = new ArrayList<>();
        this.elements.add(element);
        this.delta = new Point(delta);
        this.previousPositions = new HashMap<>();
        storePreviousPositions();
    }
    
    public MoveElementCommand(List<SlideElement> elements, Point delta) {
        this.elements = new ArrayList<>(elements);
        this.delta = new Point(delta);
        this.previousPositions = new HashMap<>();
        storePreviousPositions();
    }
    
    private void storePreviousPositions() {
        for (SlideElement element : elements) {
            previousPositions.put(element, new Point(element.getPosition()));
        }
    }
    
    @Override
    public void execute() {
        for (SlideElement element : elements) {
            Point currentPos = element.getPosition();
            Point newPos = new Point(
                currentPos.x + delta.x,
                currentPos.y + delta.y
            );
            element.setPosition(newPos);
        }
    }
    
    @Override
    public void undo() {
        for (SlideElement element : elements) {
            Point previousPos = previousPositions.get(element);
            if (previousPos != null) {
                element.setPosition(new Point(previousPos));
            }
        }
    }
}

