package raf.graffito.dsw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class GraffNode {
    @JsonIgnore
    private GraffNode parent;

    public GraffNode(GraffNode parent) {
        this.parent = parent;
    }

    public GraffNode getParent() {
        return parent;
    }

    public void setParent(GraffNode parent) {
        this.parent = parent;
    }

    public abstract void execute();

    public abstract String getNodeName();

    public abstract void setNodeName(String nodeName);

    public GraffNode findByName(String name) {
        return getNodeName().equals(name) ? this : null;
    }

    @Override
    public String toString() {
        return getNodeName();
    }
}
