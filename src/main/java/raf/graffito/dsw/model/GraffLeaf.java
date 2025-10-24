package raf.graffito.dsw.model;

public abstract class GraffLeaf extends GraffNode {

    public GraffLeaf(GraffNode parent) {
        super(parent);
    }

    @Override
    public void execute() {
        System.out.println(getClass().getSimpleName() + ": " + getNodeName());
    }

    @Override
    public GraffNode findByName(String name) {
        return getNodeName().equals(name) ? this : null;
    }
}
