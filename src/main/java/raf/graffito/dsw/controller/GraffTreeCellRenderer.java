package raf.graffito.dsw.controller;

import raf.graffito.dsw.model.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class GraffTreeCellRenderer extends DefaultTreeCellRenderer {

    private Icon workspaceIcon;
    private Icon projectIcon;
    private Icon presentationIcon;
    private Icon slideIcon;

    public GraffTreeCellRenderer() {

        workspaceIcon = scaleIcon("/images/workspace.png", 24, 24);
        projectIcon = scaleIcon("/images/project.png", 24, 24);
        presentationIcon = scaleIcon("/images/presentation.png", 24, 24);
        slideIcon = scaleIcon("/images/slide.png", 24, 24);
    }

    private Icon scaleIcon(String path, int width, int height) {
        Image img = new ImageIcon(getClass().getResource(path)).getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        GraffNode node = null;

        if (value instanceof DefaultMutableTreeNode treeNode) {
            Object userObject = treeNode.getUserObject();
            if (userObject instanceof GraffNode gn) {
                node = gn;
            }
        }

        if (node != null) {
            if (node instanceof Workspace) setIcon(workspaceIcon);
            else if (node instanceof Project) setIcon(projectIcon);
            else if (node instanceof Presentation) setIcon(presentationIcon);
            else if (node instanceof Slide) setIcon(slideIcon);
        }

        return this;
    }
}
