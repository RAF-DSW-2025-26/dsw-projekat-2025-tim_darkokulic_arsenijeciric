package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.ProjectTabController;
import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Project;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

//        tree.setDragEnabled(true);
//        tree.setDropMode(DropMode.ON_OR_INSERT);

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

        tree.setToggleClickCount(0); // iskljucuje dupli klik koji otvara i zatvara elemente stabla

        // mouse listener za dupli klip
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                    if (path == null) return;

                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    Object userObject = node.getUserObject();

                    if (userObject instanceof Project project) {
                        MainFrame.getInstance()
                                 .getActionManager()
                                 .getProjectController()
                                 .openProjectTabs(project);
                    }
                }
            }
        });

        JPopupMenu projectPopup = new JPopupMenu();
        JMenuItem openProjectItem = new JMenuItem("Open Project");
        projectPopup.add(openProjectItem);

        // akcija za desni klik misem da se otvori projekat
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (!e.isPopupTrigger()) return;

                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                if (path == null) return;

                tree.setSelectionPath(path);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object userObject = node.getUserObject();

                if (userObject instanceof Project project) {
                    // uklanja stare listener-e
                    for (ActionListener al : openProjectItem.getActionListeners()) {
                        openProjectItem.removeActionListener(al);
                    }

                    // doda novi listener
                    openProjectItem.addActionListener(ev -> {
                        MainFrame.getInstance()
                                .getActionManager()
                                .getProjectController()
                                .openProjectTabsNoColor(project);
                    });

                    projectPopup.show(tree, e.getX(), e.getY());
                }
            }
        });
        return tree;
    }

    @Override
    public void addChild(GraffNode parent, GraffNode child) {

        DefaultMutableTreeNode parentNode = findTreeNode(parent);
        if (parentNode == null) return;

        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());

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

        // update koji menja imena tabova Presentacija
        if (node instanceof Presentation pres) {
            ProjectTabController projectController = MainFrame.getInstance()
                                                              .getActionManager()
                                                              .getProjectController();

            projectController.renamePresentationTab(pres, newName);

            // update-uje desni panel sa novim informacijama
            JTabbedPane tabbedPane = MainFrame.getInstance().getTabbedPane();
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex >= 0) {
                Component component = tabbedPane.getComponentAt(selectedIndex);
                if (component instanceof JPanel panel) {
                    Presentation currentPres = (Presentation) panel.getClientProperty("presentation");
                    if (currentPres == pres && pres.getParent() instanceof Project project) {
                        MainFrame.getInstance().updateSelected(project, pres);
                    }
                }
            }
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
