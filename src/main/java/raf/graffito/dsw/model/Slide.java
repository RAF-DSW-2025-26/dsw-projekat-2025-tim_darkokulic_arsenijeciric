package raf.graffito.dsw.model;

import raf.graffito.dsw.model.observer.SlideEvent;
import raf.graffito.dsw.model.observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class Slide extends GraffNodeComposite {

    private String title;
    private List<SlideElement> elements;
    private List<Subscriber> subscribers;
    private SlideSpaceChecker spaceChecker;

    public Slide(GraffNode parent, String title) {
        super(parent);
        this.title = title;
        this.elements = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.spaceChecker = new AreaSumSpaceChecker();
    }

    public void addElement(SlideElement element) {
        elements.add(element);
        notifySlideEvent(new SlideEvent(this, element, SlideEvent.EventType.ELEMENT_ADDED));
        notifySubscribers();
    }

    public void removeElement(SlideElement element) {
        elements.remove(element);
        notifySlideEvent(new SlideEvent(this, element, SlideEvent.EventType.ELEMENT_REMOVED));
        notifySubscribers();
    }

    public List<SlideElement> getElements() {
        return elements;
    }

    public void setSpaceChecker(SlideSpaceChecker spaceChecker) {
        this.spaceChecker = spaceChecker;
    }

    public SlideSpaceChecker getSpaceChecker() {
        return spaceChecker;
    }

    public boolean hasAvailableSpace(int slideWidth, int slideHeight, double newElementArea) {
        return spaceChecker.hasAvailableSpace(elements, slideWidth, slideHeight, newElementArea);
    }

    public double getOccupiedSpacePercentage(int slideWidth, int slideHeight) {
        return spaceChecker.getOccupiedSpacePercentage(elements, slideWidth, slideHeight);
    }

    // Observer metode
    public void addSubscriber(Subscriber s) {
        if (!subscribers.contains(s)) subscribers.add(s);
    }

    public void removeSubscriber(Subscriber s) {
        subscribers.remove(s);
    }

    private void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.update(null);
        }
    }

    private void notifySlideEvent(SlideEvent event) {
        for (Subscriber s : subscribers) {
            s.onSlideEvent(event);
        }
    }

    @Override
    public void execute() { }

    @Override
    public String getNodeName() { return title; }

    @Override
    public void setNodeName(String nodeName) { this.title = nodeName; }

    @Override
    public String toString() { return title; }
}
