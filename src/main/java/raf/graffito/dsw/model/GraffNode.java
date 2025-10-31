package raf.graffito.dsw.model;

public abstract class GraffNode {
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
