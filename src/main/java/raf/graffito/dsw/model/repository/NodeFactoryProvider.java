package raf.graffito.dsw.model.repository;

import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Project;
import raf.graffito.dsw.model.Slide;

public class NodeFactoryProvider {

    public static NodeFactory getFactory(Class<? extends GraffNode> nodeType) {
        if (nodeType == Project.class) {
            return new ProjectFactory();
        } else if (nodeType == Presentation.class) {
            return new PresentationFactory();
        } else if (nodeType == Slide.class) {
            return new SlideFactory();
        }
        return null;
    }
}
