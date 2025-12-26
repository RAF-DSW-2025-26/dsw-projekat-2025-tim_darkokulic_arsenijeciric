package raf.graffito.dsw.gui.swing.jtree;

import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.SlideElement;

import javax.swing.*;

public interface GraffTree {

    JTree generateTree(GraffNode root);

    void addChild(GraffNode parent, GraffNode child);

    void removeNode(GraffNode node);

    void renameNode(GraffNode node, String newName);

    GraffNode getSelectedNode();

    public JTree getTree();

    void addSlideElement(Slide slide, SlideElement element);

    void removeSlideElement(Slide slide, SlideElement element);
}
