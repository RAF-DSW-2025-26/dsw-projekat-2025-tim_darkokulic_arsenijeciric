package raf.graffito.dsw.model.repository;

import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Presentation;

public class PresentationFactory implements NodeFactory{

    @Override
    public GraffNode createNode(GraffNode parent, Object... params) {
        String name = params.length > 0 ? (String) params[0] : "New Presentation";
        return new Presentation(parent, name);
    }
}
