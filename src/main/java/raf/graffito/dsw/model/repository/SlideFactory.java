package raf.graffito.dsw.model.repository;

import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Slide;

public class SlideFactory implements NodeFactory{

    @Override
    public GraffNode createNode(GraffNode parent, Object... params) {
        String title = params.length > 0 ? (String) params[0] : "New Slide";
        String content = params.length > 1 ? (String) params[1] : "Empty";
        return new Slide(parent, title, content);
    }

}
