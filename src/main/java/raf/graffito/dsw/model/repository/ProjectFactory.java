package raf.graffito.dsw.model.repository;

import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Project;

public class ProjectFactory implements NodeFactory{

    @Override
    public GraffNode createNode(GraffNode parent, Object... params) {
        String name = params.length > 0 ? (String) params[0] : "New Project";
        String author = params.length > 1 ? (String) params[1] : "Unknown";
        return new Project(parent, name, author);
    }
}
