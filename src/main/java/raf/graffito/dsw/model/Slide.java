package raf.graffito.dsw.model;

public class Slide extends GraffLeaf{

    private String title;
    private String content;

    public Slide(GraffNode parent, String title, String content) {
        super(parent);
        this.title = title;
        this.content = content;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getNodeName() {
        return title;
    }

    @Override
    public void setNodeName(String nodeName) {
        title = nodeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return title;
    }
}
