package raf.graffito.dsw.model;

import java.util.ArrayList;
import java.util.List;

public abstract class GraffNodeComposite extends GraffNode {

    private List<GraffNode> children;

    public GraffNodeComposite(GraffNode parent) {
        super(parent);
        this.children = new ArrayList<>();
    }

    public void addChild(GraffNode child) {
        children.add(child);
    }

    public void removeChild(GraffNode child) {
        children.remove(child);
    }

    public List<GraffNode> getChildren() {
        return children;
    }

    @Override
    public GraffNode findByName(String name) {
        // ispituje da li je trenutni Node pravi
        if(getNodeName().equals(name))
            return this;

        // ispituje na nivou dece ovog Node-a da li su pravi
        for(GraffNode child : getChildren()) {
            if(child.getNodeName().equals(name))
                return child;
        }

        // ispituje na jednom nivou dublje, da li su deca svakog deteta Node-a pravi
        for(GraffNode child : getChildren()) {
            if(child instanceof GraffNodeComposite composite){
                for(GraffNode grandchild : composite.getChildren()) {
                    if(grandchild.getNodeName().equals(name))
                        return grandchild;
                }
            }
        }

        return null;
    }
}
