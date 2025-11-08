package raf.graffito.dsw.model.repository;

import raf.graffito.dsw.model.GraffNode;

public interface NodeFactory {

    GraffNode createNode(GraffNode parent, Object... params);
}
