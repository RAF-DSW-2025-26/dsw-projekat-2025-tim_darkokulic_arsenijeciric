package raf.graffito.dsw.model;

public class Workspace extends GraffNodeComposite{

    private String name;

    public Workspace(String name) {
        super(null);   // root nema parent-a
        this.name = name;
    }

    @Override
    public void execute() {

    }

    @Override
    public void addChild(GraffNode child) {
        if (child instanceof Project){
            super.addChild(child);
        }else{
            /// Ovde mozemo implementirati kasnije error poruke
        }
    }

    @Override
    public String getNodeName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
