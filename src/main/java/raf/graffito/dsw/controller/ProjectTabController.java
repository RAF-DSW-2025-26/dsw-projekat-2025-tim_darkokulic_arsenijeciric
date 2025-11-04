package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;
import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Project;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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
            return chooseProjectColor(project); // try again
        } else {
            usedColors.add(selectedColor);
            return selectedColor;
        }
    }

    public void openProjectTabs(Project project) {
        presentationView.clearAllTabs();

        Color projectColor = chooseProjectColor(project);
        if (projectColor == null) return;
        project.setColor(projectColor);

        for (GraffNode child : project.getChildren()) {
            if (child instanceof Presentation pres) {
                presentationView.addPresentationTab(pres, projectColor);
            }
        }

        Presentation firstPresentation = null;
        if (!project.getChildren().isEmpty() && project.getChildren().get(0) instanceof Presentation) {
            firstPresentation = (Presentation) project.getChildren().get(0);
        }
        mainFrame.updateSelected(project, firstPresentation);
    }

    // helper funkcija koja bypass-uje biranje boja sa desnim klikom
    public void openProjectTabsNoColor(Project project) {
        presentationView.clearAllTabs();

        Color projectColor = project.getColor() != null ? project.getColor() : Color.LIGHT_GRAY;

        for (GraffNode child : project.getChildren()) {
            if (child instanceof Presentation pres) {
                presentationView.addPresentationTab(pres, projectColor);
            }
        }

        Presentation firstPresentation = null;
        if (!project.getChildren().isEmpty() && project.getChildren().get(0) instanceof Presentation) {
            firstPresentation = (Presentation) project.getChildren().get(0);
        }
        mainFrame.updateSelected(project, firstPresentation);
    }

    public void addPresentationTab(Presentation pres, Color projectColor) {
        presentationView.addPresentationTab(pres, projectColor);
    }

    public void renamePresentationTab(Presentation pres, String newName) {
        presentationView.renamePresentationTab(pres, newName);
    }
}
