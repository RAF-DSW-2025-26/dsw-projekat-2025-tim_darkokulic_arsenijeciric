package raf.graffito.dsw.model.observer;

import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.SlideElement;

public class SlideEvent {

    public enum EventType {
        ELEMENT_ADDED,
        ELEMENT_REMOVED
    }

    private final Slide slide;
    private final SlideElement element;
    private final EventType eventType;

    public SlideEvent(Slide slide, SlideElement element, EventType eventType) {
        this.slide = slide;
        this.element = element;
        this.eventType = eventType;
    }

    public Slide getSlide() {
        return slide;
    }

    public SlideElement getElement() {
        return element;
    }

    public EventType getEventType() {
        return eventType;
    }
}
