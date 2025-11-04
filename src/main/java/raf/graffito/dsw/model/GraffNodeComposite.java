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

        // Rekurzivno pretražuje svu decu
        for (GraffNode child : getChildren()) {
            GraffNode found = child.findByName(name);
            if (found != null) {
                return found;
            }
        }
        // ako nista nije pronadjeno
        return null;
    }
}
