package raf.graffito.dsw.controller;

import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.controller.state.StateManager;
import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;
import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.repository.GraffRepository;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static javax.swing.SwingConstants.EAST;
import static javax.swing.SwingConstants.WEST;

public class ProjectTabController {

    private final MainFrame mainFrame;
    private final PresentationView presentationView;
    private final Set<Color> usedColors = new HashSet<>();

    public ProjectTabController(MainFrame mainFrame, PresentationView presentationView) {
        this.mainFrame = mainFrame;
        this.presentationView = presentationView;

        // Listener koji slusa kada menjamo tab
        presentationView.getTabbedPane().addChangeListener(e -> {
            Presentation pres = presentationView.getCurrentPresentation();
            if (pres != null && pres.getParent() instanceof Project project) {
                mainFrame.updateSelected(project, pres);
            }
        });
    }

    private Color chooseProjectColor(Project project) {
        Color initialColor = Color.WHITE;

        JColorChooser colorChooser = new JColorChooser(initialColor);
        colorChooser.setPreviewPanel(new JPanel());

        AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
        // Uklanja druge color picker-e
        for (AbstractColorChooserPanel accp : panels) {
            String name = accp.getDisplayName();
            if (!name.equals("Swatches")) {
                colorChooser.removeChooserPanel(accp);
            }
        }

        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                colorChooser,
                "Choose Project Color",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) return null;

        Color selectedColor = colorChooser.getColor();

        if (usedColors.contains(selectedColor)) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "This color is already used by another project. Please choose a different color.",
                    "Color Already Used",
                    JOptionPane.WARNING_MESSAGE
            );
            return chooseProjectColor(project);
        } else {
            usedColors.add(selectedColor);
            return selectedColor;
        }
    }

    public void openProjectTabs(Project project) {
        presentationView.clearAllTabs();

        GraffRepository repository = ApplicationFramework.getInstance().getRepository();
        Color projectColor = chooseProjectColor(project);
        if (projectColor == null) return;

        ColorDecorator decoratedProject = new ColorDecorator(project, projectColor);
        repository.setProjectDecorator(project, decoratedProject);

        presentationView.setCurrentlyOpenProject(project);

        for (GraffNode child : project.getChildren()) {
            if (child instanceof Presentation pres) {
                StateManager stateManager = new StateManager();
                UndoRedoManager undoRedoManager = new UndoRedoManager();
                presentationView.addPresentationTab(pres, projectColor, stateManager, undoRedoManager);
            } else if (child instanceof Slide slide) {
                StateManager stateManager = new StateManager();
                UndoRedoManager undoRedoManager = new UndoRedoManager();
                presentationView.addDirectSlideTab(slide, projectColor, stateManager, undoRedoManager);
            }
        }

        Presentation firstPresentation = null;
        if (!project.getChildren().isEmpty() && project.getChildren().get(0) instanceof Presentation) {
            firstPresentation = (Presentation) project.getChildren().get(0);
        }
        mainFrame.updateSelected(project, firstPresentation);
    }

    // helper funkcija koja bypass-uje biranje boja sa desnim klikom
    public void openProjectTabsNoColor(Project project, ColorDecorator decorator) {
        presentationView.clearAllTabs();

        Color projectColor = (decorator != null) ? decorator.getColor() : Color.WHITE;

        presentationView.setCurrentlyOpenProject(project);

        for (GraffNode child : project.getChildren()) {
            if (child instanceof Presentation pres) {
                StateManager stateManager = new StateManager();
                UndoRedoManager undoRedoManager = new UndoRedoManager();
                presentationView.addPresentationTab(pres, projectColor, stateManager, undoRedoManager);
            } else if (child instanceof Slide slide) {
                StateManager stateManager = new StateManager();
                UndoRedoManager undoRedoManager = new UndoRedoManager();
                presentationView.addDirectSlideTab(slide, projectColor, stateManager, undoRedoManager);
            }
        }

        Presentation firstPresentation = null;
        if (!project.getChildren().isEmpty() && project.getChildren().get(0) instanceof Presentation) {
            firstPresentation = (Presentation) project.getChildren().get(0);
        }
        mainFrame.updateSelected(project, firstPresentation);
    }

    public void addPresentationTab(Presentation pres, Color projectColor) {
        StateManager stateManager = new StateManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager();
        presentationView.addPresentationTab(pres, projectColor, stateManager, undoRedoManager);
    }

    public void renamePresentationTab(Presentation pres, String newName) {
        presentationView.renamePresentationTab(pres, newName);
    }


}
