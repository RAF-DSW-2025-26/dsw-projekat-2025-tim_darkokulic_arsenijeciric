package raf.graffito.dsw.model;

public class Presentation extends GraffNodeComposite{

    private String name;

    public Presentation(GraffNode parent, String name) {
        super(parent);
        this.name = name;
    }


    @Override
    public void execute() {

    }

    @Override
    public void addChild(GraffNode child) {
        if (child instanceof Slide) {
            super.addChild(child);
        } else {
            /// Ovde mozemo kasnije implementirati error poruke
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
