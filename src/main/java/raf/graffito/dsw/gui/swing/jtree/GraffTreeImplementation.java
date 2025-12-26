package raf.graffito.dsw.gui.swing.jtree;

import raf.graffito.dsw.controller.GraffTreeTransferHandler;
import raf.graffito.dsw.controller.ProjectTabController;
import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.repository.GraffRepository;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

public class GraffTreeImplementation implements GraffTree {

    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;

    public GraffTreeImplementation() {
    }

    @Override
    public JTree generateTree(GraffNode root) {

        rootNode = new DefaultMutableTreeNode(root);
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);

        // Set GraffTree reference for all existing slides
        setTreeReferenceForAllSlides(root);

        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        tree.setDragEnabled(true);
        tree.setDropMode(DropMode.ON_OR_INSERT);
        tree.setTransferHandler(new GraffTreeTransferHandler());

        // Listener koji aktivira dugme za editovanje projekata kada je selektovan projekat
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

        tree.setToggleClickCount(0);

        // Listener koji slusa za dupli klik da enable-uje slektovanje boje
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
                    } else if (userObject instanceof Slide slide) {
                        MainFrame.getInstance()
                                .getActionManager()
                                .getSlideController()
                                .openSlide(slide);
                    }
                }
            }
        });

        JPopupMenu projectPopup = new JPopupMenu();
        JMenuItem openProjectItem = new JMenuItem("Open Project");
        projectPopup.add(openProjectItem);

        // Listener za desni klik koji omogucava otvaranje projekta bez dodeljivanja boje
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
                    for (ActionListener al : openProjectItem.getActionListeners()) {
                        openProjectItem.removeActionListener(al);
                    }

                    openProjectItem.addActionListener(ev -> {
                        GraffRepository repo = ApplicationFramework.getInstance().getRepository();
                        ColorDecorator decorator = repo.getProjectDecorator(project);

                        MainFrame.getInstance()
                                 .getActionManager()
                                 .getProjectController()
                                 .openProjectTabsNoColor(project, decorator);
                    });

                    projectPopup.show(tree, e.getX(), e.getY());
                }
            }
        });
        return tree;
    }

    // Samo update-uje izgled Tree-a, model je vec update-ovan
    @Override
    public void addChild(GraffNode parent, GraffNode child) {

        DefaultMutableTreeNode parentNode = findTreeNode(parent);
        if (parentNode == null) return;

        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());

        // If the child is a Slide, set the GraffTree reference so it can update the tree when elements are added
        if (child instanceof Slide slide) {
            slide.setGraffTree(this);
        }

        TreePath path = new TreePath(parentNode.getPath());
        tree.expandPath(path);
    }

    // Samo update-uje izgled Tree-a, model je vec update-ovan
    @Override
    public void removeNode(GraffNode node) {

        DefaultMutableTreeNode treeNode = findTreeNode(node);
        if (treeNode != null && treeNode != rootNode) {
            treeModel.removeNodeFromParent(treeNode);
        }
    }

    // Samo update-uje izgled Tree-a, model je vec update-ovan
    @Override
    public void renameNode(GraffNode node, String newName) {

        DefaultMutableTreeNode treeNode = findTreeNode(node);
        if (treeNode != null) {
            treeModel.nodeChanged(treeNode);
        }

        if (node instanceof Presentation pres) {
            ProjectTabController projectController = MainFrame.getInstance()
                                                              .getActionManager()
                                                              .getProjectController();

            projectController.renamePresentationTab(pres, newName);

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

    @Override
    public void addSlideElement(Slide slide, SlideElement element) {
        DefaultMutableTreeNode slideNode = findTreeNode(slide);
        if (slideNode == null) return;

        DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode(element);
        treeModel.insertNodeInto(elementNode, slideNode, slideNode.getChildCount());

        TreePath path = new TreePath(slideNode.getPath());
        tree.expandPath(path);
    }

    @Override
    public void removeSlideElement(Slide slide, SlideElement element) {
        DefaultMutableTreeNode slideNode = findTreeNode(slide);
        if (slideNode == null) return;

        for (int i = 0; i < slideNode.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) slideNode.getChildAt(i);
            if (child.getUserObject() == element) {
                treeModel.removeNodeFromParent(child);
                break;
            }
        }
    }

    private void setTreeReferenceForAllSlides(GraffNode node) {
        if (node instanceof Slide slide) {
            slide.setGraffTree(this);
        }

        if (node instanceof GraffNodeComposite composite) {
            for (GraffNode child : composite.getChildren()) {
                setTreeReferenceForAllSlides(child);
            }
        }
    }
}