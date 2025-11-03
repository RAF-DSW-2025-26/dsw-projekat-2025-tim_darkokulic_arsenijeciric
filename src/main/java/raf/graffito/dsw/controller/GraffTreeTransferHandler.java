package raf.graffito.dsw.controller;

import raf.graffito.dsw.model.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class GraffTreeTransferHandler extends TransferHandler {

    private DataFlavor nodesFlavor;
    private DataFlavor[] flavors = new DataFlavor[1];

    public GraffTreeTransferHandler() {
        try {
            String mimeType = DataFlavor.javaJVMLocalObjectMimeType +
                    ";class=\"" + DefaultMutableTreeNode.class.getName() + "\"";
            nodesFlavor = new DataFlavor(mimeType);
            flavors[0] = nodesFlavor;
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFound: " + e.getMessage());
        }
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }

        support.setShowDropLocation(true);

        if (!support.isDataFlavorSupported(nodesFlavor)) {
            return false;
        }

        // pronalazi destinaciju stavljanja
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        TreePath dest = dl.getPath();

        if (dest == null) {
            return false;
        }

        DefaultMutableTreeNode target = (DefaultMutableTreeNode) dest.getLastPathComponent();
        Object targetUserObject = target.getUserObject();

        // uzima to sto prenosimo
        try {
            Transferable t = support.getTransferable();
            DefaultMutableTreeNode draggedNode = (DefaultMutableTreeNode) t.getTransferData(nodesFlavor);
            Object draggedUserObject = draggedNode.getUserObject();

            // dozvoljava samo prevlacenje slajdova
            if (!(draggedUserObject instanceof Slide)) {
                return false;
            }

            // Moze samo da spusti u prezentaciju ili izmedju drugih slajdova
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) draggedNode.getParent();

            // slucaj ako se spusti tacno na Node
            if (dl.getChildIndex() == -1) {
                // sme da spusti na prezentaciju
                if (targetUserObject instanceof Presentation) {
                    return true;
                }
                // ne sme da spusti ni na sta drugo
                return false;
            } else {
                // ako se spusta izmedju Node-ova, proverava da li je roditelj prezentazija (onda zna da je medju slajdovima)
                return targetUserObject instanceof Presentation;
            }

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;
        TreePath path = tree.getSelectionPath();
        if (path != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

            // samo dopusta prenosenje slajdova
            if (node.getUserObject() instanceof Slide) {
                return new NodesTransferable(node);
            }
        }
        return null;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        try {
            Transferable t = support.getTransferable();
            DefaultMutableTreeNode draggedNode = (DefaultMutableTreeNode) t.getTransferData(nodesFlavor);

            JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
            int childIndex = dl.getChildIndex();
            TreePath dest = dl.getPath();
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) dest.getLastPathComponent();

            JTree tree = (JTree) support.getComponent();
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

            // uzima starog Parent-a pre premestanja
            DefaultMutableTreeNode oldParent = (DefaultMutableTreeNode) draggedNode.getParent();
            GraffNode oldParentUserObject = (GraffNode) oldParent.getUserObject();
            GraffNode draggedUserObject = (GraffNode) draggedNode.getUserObject();

            // ukoliko se spusta na prezentaciju (a ne na slajd)
            if (childIndex == -1) {
                if (parent.getUserObject() instanceof Presentation) {
                    // uklanja se od starog Parent-a
                    if (oldParentUserObject instanceof GraffNodeComposite composite) {
                        composite.removeChild(draggedUserObject);
                    }
                    model.removeNodeFromParent(draggedNode);

                    // stavlja se na novog Parent-a na kraju
                    if (parent.getUserObject() instanceof GraffNodeComposite composite) {
                        composite.addChild(draggedUserObject);
                        draggedUserObject.setParent((GraffNode) parent.getUserObject());
                    }
                    model.insertNodeInto(draggedNode, parent, parent.getChildCount());

                    return true;
                }
            } else {
                // spustanje izmedju slajdova u prezentaciji
                if (parent.getUserObject() instanceof Presentation) {
                    // ukloni sa stare lokacije
                    int oldIndex = oldParent.getIndex(draggedNode);

                    if (oldParentUserObject instanceof GraffNodeComposite composite) {
                        composite.removeChild(draggedUserObject);
                    }
                    model.removeNodeFromParent(draggedNode);

                    // podesi index ukoliko se nalazi unutar istog Parent-a
                    if (oldParent == parent && oldIndex < childIndex) {
                        childIndex--;
                    }

                    // postavi na novu lokaciju
                    if (parent.getUserObject() instanceof GraffNodeComposite composite) {
                        composite.getChildren().add(childIndex, draggedUserObject);
                        draggedUserObject.setParent((GraffNode) parent.getUserObject());
                    }
                    model.insertNodeInto(draggedNode, parent, childIndex);

                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Unutrasnja klasa za Transferable
    private class NodesTransferable implements Transferable {
        private DefaultMutableTreeNode node;

        public NodesTransferable(DefaultMutableTreeNode node) {
            this.node = node;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return node;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return nodesFlavor.equals(flavor);
        }
    }
}