package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
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
    private final JTabbedPane tabbedPane;
    private final Set<Color> usedColors = new HashSet<>();

    public ProjectTabController(MainFrame mainFrame, JTabbedPane tabbedPane) {
        this.mainFrame = mainFrame;
        this.tabbedPane = tabbedPane;

        // listener koji proverava da li smo promenili tab
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex >= 0) {
                Component component = tabbedPane.getComponentAt(selectedIndex);
                if (component instanceof JPanel panel) {
                    Presentation pres = (Presentation) panel.getClientProperty("presentation");
                    if (pres != null && pres.getParent() instanceof Project project) {
                        mainFrame.updateSelected(project, pres);
                    }
                }
            }
        });
    }

    private Color chooseProjectColor(Project project) {
        Color initialColor = Color.WHITE;

        JColorChooser colorChooser = new JColorChooser(initialColor);

        colorChooser.setPreviewPanel(new JPanel());

        AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
        // brisemo ostale tabove panela za biranja boje
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
        tabbedPane.removeAll();

        Color projectColor = chooseProjectColor(project);
        if (projectColor == null) return;
        project.setColor(projectColor);

        for (GraffNode child : project.getChildren()) {
            if (child instanceof Presentation pres) {
                addPresentationTab(pres, projectColor);
            }
        }

        Presentation firstPresentation = null;
        if (!project.getChildren().isEmpty() && project.getChildren().get(0) instanceof Presentation) {
            firstPresentation = (Presentation) project.getChildren().get(0);
        }
        mainFrame.updateSelected(project, firstPresentation);
    }

    // pomocna funkcija za otvaranje desnim klikom koja bypass-uje biranje boja
    public void openProjectTabsNoColor(Project project) {
        tabbedPane.removeAll();

        Color projectColor = project.getColor() != null ? project.getColor() : Color.LIGHT_GRAY;

        for (GraffNode child : project.getChildren()) {
            if (child instanceof Presentation pres) {
                addPresentationTab(pres, projectColor);
            }
        }

        Presentation firstPresentation = null;
        if (!project.getChildren().isEmpty() && project.getChildren().get(0) instanceof Presentation) {
            firstPresentation = (Presentation) project.getChildren().get(0);
        }
        mainFrame.updateSelected(project, firstPresentation);
    }

    public void addPresentationTab(Presentation pres, Color projectColor) {
        JPanel slidePanel = new JPanel();
        slidePanel.setBackground(Color.WHITE);

        slidePanel.putClientProperty("presentation", pres);

        tabbedPane.addTab(pres.getNodeName(), slidePanel);

        int index = tabbedPane.indexOfComponent(slidePanel);
        JLabel tabLabel = new JLabel(pres.getNodeName());
        tabLabel.setOpaque(true);
        tabLabel.setBackground(projectColor);
        tabLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        tabbedPane.setTabComponentAt(index, tabLabel);
    }

    public void renamePresentationTab(Presentation pres, String newName) {
        // metoda koja uzivo update-uje imena tabova kada se koristi rename dugme
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component component = tabbedPane.getComponentAt(i);

            if (component instanceof JPanel panel) {
                Object tabPresentation = panel.getClientProperty("presentation");
                if (tabPresentation == pres) {
                    Component tabComponent = tabbedPane.getTabComponentAt(i);
                    if (tabComponent instanceof JLabel label) {
                        label.setText(newName);
                    }
                    break;
                }
            }
        }
    }
}
