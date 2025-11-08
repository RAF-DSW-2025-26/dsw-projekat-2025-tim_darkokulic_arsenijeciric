package raf.graffito.dsw.model.repository;

import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.observer.MessageGenerator;

import java.util.HashMap;
import java.util.Map;

public class GraffRepository {

    private Workspace workspace;
    private MessageGenerator messageGenerator;
    private Map<Project, ColorDecorator> projectDecorators;

    public GraffRepository(Workspace workspace) {
        this.workspace = workspace;
        this.messageGenerator = new MessageGenerator();
        this.projectDecorators = new HashMap<>();
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public ColorDecorator getProjectDecorator(Project project) {
        return projectDecorators.get(project);
    }

    public void setProjectDecorator(Project project, ColorDecorator decorator) {
        projectDecorators.put(project, decorator);
    }

    public boolean hasProjectDecorator(Project project) {
        return projectDecorators.containsKey(project);
    }

    public void removeProjectDecorator(Project project) {
        projectDecorators.remove(project);
    }

    public GraffNode createNode(GraffNode parent, Class<? extends GraffNode> nodeType, Object... params) {

        if (!canAddChild(parent, nodeType)) {
            messageGenerator.notifySubscribers(
                    "Ne možete dodati " + getNodeTypeName(nodeType) + " u " + getNodeTypeName(parent.getClass()),
                    "GREŠKA"
            );
            return null;
        }

        // niz objekata params, ako ima vise od jednog clana, uzima clan na prvom mestu i castuje ga u String
        // ako nema, uzima Default ima za taj node

        String nodeName = params.length > 0 ? (String) params[0] : getDefaultName(nodeType);

        if (!isDefaultName(nodeName) && hasDuplicateName(parent, nodeName)) {
            messageGenerator.notifySubscribers(
                    "Čvor sa imenom '" + nodeName + "' već postoji.",
                    "GREŠKA"
            );
            return null;
        }

        NodeFactory factory = NodeFactoryProvider.getFactory(nodeType);

        if (factory == null) {
            messageGenerator.notifySubscribers(
                    "Ne postoji fabrika za tip: " + nodeType.getSimpleName(),
                    "GREŠKA"
            );
            return null;
        }

        GraffNode newNode = null;

        try {
            newNode = factory.createNode(parent, params);
        } catch (Exception e) {
            messageGenerator.notifySubscribers(
                    "Greška pri kreiranju čvora: " + e.getMessage(),
                    "GREŠKA"
            );
            return null;
        }

        if (newNode != null) {
            if (parent instanceof GraffNodeComposite composite) {
                composite.addChild(newNode);
            }

            messageGenerator.notifySubscribers(
                    "Uspešno kreiran " + getNodeTypeName(nodeType) + ": " + newNode.getNodeName(),
                    "INFO"
            );
        }

        return newNode;
    }

    // pomocna funkcija koja nam dozvoljava da pravimo vise Node-ova sa Default imenima
    // a da se ne izazove error za node-ove sa istim imenima
    private boolean isDefaultName(String name) {
        return "New Project".equals(name) ||
                "New Presentation".equals(name) ||
                "New Slide".equals(name);
    }

    public boolean removeNode(GraffNode node) {

        if (node == null) {
            messageGenerator.notifySubscribers("Ne možete obrisati nepostojeći čvor.", "GREŠKA");
            return false;
        }

        if (node instanceof Workspace) {
            messageGenerator.notifySubscribers("Ne možete obrisati Workspace.", "GREŠKA");
            return false;
        }

        if (node.getParent() == null) {
            messageGenerator.notifySubscribers("Ne možete obrisati root čvor.", "GREŠKA");
            return false;
        }

        GraffNode parent = node.getParent();


        if (parent instanceof Project project) {
            if (project.getChildren().size() <= 1) {
                messageGenerator.notifySubscribers(
                        "Ne možete obrisati: Projekat mora imati barem jednu prezentaciju ili slajd.",
                        "GREŠKA"
                );
                return false;
            }
        }

        if (node instanceof Project project) {
            removeProjectDecorator(project);
        }

        if (parent instanceof GraffNodeComposite composite) {
            composite.removeChild(node);
            messageGenerator.notifySubscribers(
                    "Uspešno obrisan " + getNodeTypeName(node.getClass()) + ": " + node.getNodeName(),
                    "INFO"
            );
            return true;
        }

        return false;
    }


    public boolean renameNode(GraffNode node, String newName) {

        if (node == null || newName == null || newName.isBlank()) {
            messageGenerator.notifySubscribers("Ime ne može biti prazno.", "GREŠKA");
            return false;
        }


        if (node.getParent() != null && hasDuplicateNameExcluding(node.getParent(), newName, node)) {
            messageGenerator.notifySubscribers(
                    "Čvor sa imenom '" + newName + "' već postoji na ovoj putanji.",
                    "GREŠKA"
            );
            return false;
        }

        String oldName = node.getNodeName();
        node.setNodeName(newName);

        return true;
    }

    private boolean hasDuplicateName(GraffNode parent, String name) {
        if (!(parent instanceof GraffNodeComposite composite)) {
            return false;
        }

        for (GraffNode child : composite.getChildren()) {
            if (child.getNodeName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    // Pomocna funkcija koja dozvoljava da se node imenuje ponovo sa istim imenom
    private boolean hasDuplicateNameExcluding(GraffNode parent, String name, GraffNode excludeNode) {
        if (!(parent instanceof GraffNodeComposite composite)) {
            return false;
        }

        for (GraffNode child : composite.getChildren()) {
            if (child != excludeNode && child.getNodeName().equals(name)) {
                return true;
            }
        }

        return false;
    }


    public boolean canAddChild(GraffNode parent, Class<? extends GraffNode> childType) {

        if (parent == null || childType == null) {
            return false;
        }

        // Workspace -> samo Project
        if (parent instanceof Workspace) {
            return childType == Project.class;
        }

        // Project -> samo Presentation ili Slide
        if (parent instanceof Project) {
            return childType == Presentation.class || childType == Slide.class;
        }

        // Presentation -> samo Slide
        if (parent instanceof Presentation) {
            return childType == Slide.class;
        }

        // Slide -> nista (Leaf Node)
        if (parent instanceof Slide) {
            return false;
        }

        return false;
    }

    private boolean canRemoveChildFromProject(Project project, GraffNode nodeToRemove) {
        if (project == null || nodeToRemove == null) {
            return false;
        }

        // Projekat mora da ima vise od jednog deteta da bi dopustio uklanjanje
        return project.getChildren().size() > 1;
    }

    public int getProjectCount() {
        int count = 0;
        for (GraffNode child : workspace.getChildren()) {
            if (child instanceof Project) {
                count++;
            }
        }
        return count;
    }

    private String getDefaultName(Class<? extends GraffNode> nodeType) {
        if (nodeType == Project.class) return "New Project";
        if (nodeType == Presentation.class) return "New Presentation";
        if (nodeType == Slide.class) return "New Slide";
        return "New Node";
    }

    private String getNodeTypeName(Class<? extends GraffNode> nodeType) {
        if (nodeType == Workspace.class) return "Workspace";
        if (nodeType == Project.class) return "Projekat";
        if (nodeType == Presentation.class) return "Prezentaciju";
        if (nodeType == Slide.class) return "Slajd";
        return "Čvor";
    }


    public void clearAll() {
        workspace.getChildren().clear();
        projectDecorators.clear();
    }

    @Override
    public String toString() {
        return "GraffRepository [Workspace: " + workspace.getNodeName() +
                ", Projects: " + getProjectCount() + "]";
    }

}