package raf.graffito.dsw.model;

public abstract class GraffNodeDecorator extends GraffNode {

    protected GraffNode decoratedNode;

    public GraffNodeDecorator(GraffNode node) {
        super(node.getParent());
        this.decoratedNode = node;
    }

    @Override
    public void execute() {
        decoratedNode.execute();
    }

    @Override
    public String getNodeName() {
        return decoratedNode.getNodeName();
    }

    @Override
    public void setNodeName(String name) {
        decoratedNode.setNodeName(name);
    }
}
