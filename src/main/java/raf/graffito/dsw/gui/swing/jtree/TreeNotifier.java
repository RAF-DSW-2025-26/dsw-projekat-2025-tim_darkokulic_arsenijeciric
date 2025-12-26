package raf.graffito.dsw.gui.swing.jtree;

import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Project;
import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.SlideElement;
import raf.graffito.dsw.model.observer.Message;
import raf.graffito.dsw.model.observer.Subscriber;

public class TreeNotifier implements Subscriber {

    private GraffTree graffTree;
    private Slide slide;

    public TreeNotifier(GraffTree graffTree, Slide slide) {
        this.graffTree = graffTree;
        this.slide = slide;
    }

    @Override
    public void update(Message message) {
    }

    @Override
    public void updateSelected(Project project, Presentation presentation) {
    }

    public void elementAdded(SlideElement element) {
        graffTree.addSlideElement(slide, element);
    }

    public void elementRemoved(SlideElement element) {
        graffTree.removeSlideElement(slide, element);
    }
}
