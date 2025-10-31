package raf.graffito.dsw.model;

public class Project extends GraffNodeComposite{

    private String name;
    private String author;
    private Integer number;

    public Project(GraffNode parent, String name, String author) {
        super(parent);
        this.name = name;
        this.author = author;
        this.number = 0;
    }

    @Override
    public void execute() {

    }

    @Override
    public void addChild (GraffNode child) {
        if (child instanceof Presentation || child instanceof Slide) {
            super.addChild(child);
            number++;
        }else{
            /// ovde mozemo implementirati kasnije error poruke
        }
    }

    public boolean isValid() {
        return !getChildren().isEmpty(); // provera da li Project zapravo ima Presentation ili Slide u sebi
    }

    @Override
    public String getNodeName() {
        return name;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.name = nodeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return name;
    }
}
