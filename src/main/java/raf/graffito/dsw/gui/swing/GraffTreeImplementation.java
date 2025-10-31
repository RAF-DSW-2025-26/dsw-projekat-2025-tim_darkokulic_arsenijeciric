package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Project;

import javax.swing.*;
import javax.swing.tree.*;
import java.util.Enumeration;

public class GraffTreeImplementation implements GraffTree{

    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;


    @Override
    public JTree generateTree(GraffNode root) {

        rootNode = new DefaultMutableTreeNode(root);
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);

        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        tree.addTreeSelectionListener(e -> {
            Object selected = tree.getLastSelectedPathComponent();
            boolean isProject = false;

            if (selected instanceof DefaultMutableTreeNode node) {
                Object userObject = node.getUserObject();
                if (userObject instanceof Project) {
                    isProject = true;
                }
            }

            MainFrame.getInstance()
                    .getActionManager()
                    .getEditProjectAction()
                    .setEnabled(isProject);
        });

        return tree;
    }

    @Override
    public void addChild(GraffNode parent, GraffNode child) {

        DefaultMutableTreeNode parentNode = findTreeNode(parent);
        if (parentNode == null) return;

        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());

        // expand automatically
        TreePath path = new TreePath(parentNode.getPath());
        tree.expandPath(path);

    }

    @Override
    public void removeNode(GraffNode node) {

        DefaultMutableTreeNode treeNode = findTreeNode(node);
        if (treeNode != null && treeNode != rootNode) {
            treeModel.removeNodeFromParent(treeNode);
        }
    }

    @Override
    public void renameNode(GraffNode node, String newName) {

        node.setNodeName(newName);
        DefaultMutableTreeNode treeNode = findTreeNode(node);
        if (treeNode != null) {
            treeModel.nodeChanged(treeNode);
        }
    }

    @Override
    public GraffNode getSelectedNode() {

        TreePath path = tree.getSelectionPath();
        if (path == null)
            return null;
        DefaultMutableTreeNode selected = (DefaultMutableTreeNode) path.getLastPathComponent();
        return (GraffNode) selected.getUserObject();
    }

    private DefaultMutableTreeNode findTreeNode(GraffNode node) {

        Enumeration<TreeNode> enumeration = ((DefaultMutableTreeNode) treeModel.getRoot()).breadthFirstEnumeration();
        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode current = (DefaultMutableTreeNode) enumeration.nextElement();
            if (current.getUserObject() == node) return current;
        }
        return null;
    }


    public JTree getTree() {
        return tree;
    }
}
