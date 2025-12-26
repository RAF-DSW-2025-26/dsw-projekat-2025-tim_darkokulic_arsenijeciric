package raf.graffito.dsw.model;

import raf.graffito.dsw.gui.swing.jtree.GraffTree;
import raf.graffito.dsw.model.observer.Message;
import raf.graffito.dsw.model.observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class Slide extends GraffNodeComposite {

    private String title;
    private List<SlideElement> elements;
    private List<Subscriber> subscribers; // observeri
    private GraffTree graffTree; // Reference to update tree when elements change

    public Slide(GraffNode parent, String title) {
        super(parent);
        this.title = title;
        this.elements = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public void addElement(SlideElement element) {
        elements.add(element);
        if (graffTree != null) {
            graffTree.addSlideElement(this, element);
        }
        notifySubscribers();
    }

    public void removeElement(SlideElement element) {
        elements.remove(element);
        if (graffTree != null) {
            graffTree.removeSlideElement(this, element);
        }
        notifySubscribers();
    }

    public void setGraffTree(GraffTree graffTree) {
        this.graffTree = graffTree;
    }

    public List<SlideElement> getElements() {
        return elements;
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

    @Override
    public void execute() { }

    @Override
    public String getNodeName() { return title; }

    @Override
    public void setNodeName(String nodeName) { this.title = nodeName; }

    @Override
    public String toString() { return title; }
}
