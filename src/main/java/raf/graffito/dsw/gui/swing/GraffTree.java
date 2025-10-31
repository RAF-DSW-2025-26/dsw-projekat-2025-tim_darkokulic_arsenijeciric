package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.model.GraffNode;

import javax.swing.*;

public interface GraffTree {

    JTree generateTree(GraffNode root);

    void addChild(GraffNode parent, GraffNode child);

    void removeNode(GraffNode node);

    void renameNode(GraffNode node, String newName);

    GraffNode getSelectedNode();

    public JTree getTree();
}
