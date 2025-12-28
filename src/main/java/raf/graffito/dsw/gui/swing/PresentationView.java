package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.model.GraffNode;
import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.observer.Message;
import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Project;
import raf.graffito.dsw.model.observer.Subscriber;
import raf.graffito.dsw.model.windowmode.WindowModeManager;

import javax.swing.*;
import java.awt.*;

public class PresentationView extends JPanel implements Subscriber {

    private JTabbedPane tabbedPane;
    private Project openProject;
    private WindowModeManager windowModeManager;

    public PresentationView() {
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new ColoredTabbedPaneUI(this));
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(tabbedPane, BorderLayout.CENTER);

    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void addPresentationTab(Presentation presentation, Color projectColor) {

        JPanel slideContainer = new JPanel();
        slideContainer.setLayout(new BoxLayout(slideContainer, BoxLayout.Y_AXIS));
        slideContainer.setBackground(Color.WHITE);

        double currentScaleFactor = windowModeManager != null ? windowModeManager.getCurrentScaleFactor() : 1.0;

        for (GraffNode child : presentation.getChildren()) {
            if (child instanceof Slide slide) {
                raf.graffito.dsw.gui.swing.SlideView slideView = new raf.graffito.dsw.gui.swing.SlideView(slide);
                slideView.setZoomFactor(currentScaleFactor);
                slideView.setAlignmentX(Component.CENTER_ALIGNMENT);
                slideContainer.add(Box.createVerticalStrut(10));
                slideContainer.add(slideView);
            }
        }
        slideContainer.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(slideContainer);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel slidePanel = new JPanel(new BorderLayout());
        slidePanel.setBackground(Color.WHITE);
        slidePanel.add(scrollPane, BorderLayout.CENTER);
        slidePanel.putClientProperty("presentation", presentation);

        tabbedPane.addTab(presentation.getNodeName(), slidePanel);

        int index = tabbedPane.indexOfComponent(slidePanel);
        tabbedPane.setTabComponentAt(index, new JLabel("  " + presentation.getNodeName() + "  "));
    }

    public void addDirectSlideTab(Slide slide, Color projectColor) {
        raf.graffito.dsw.gui.swing.SlideView slideView = new raf.graffito.dsw.gui.swing.SlideView(slide);

        double currentScaleFactor = windowModeManager != null ? windowModeManager.getCurrentScaleFactor() : 1.0;
        slideView.setZoomFactor(currentScaleFactor);

        JPanel slideContainer = new JPanel();
        slideContainer.setLayout(new BoxLayout(slideContainer, BoxLayout.Y_AXIS));
        slideContainer.setBackground(Color.WHITE);
        slideContainer.add(Box.createVerticalStrut(10));
        slideContainer.add(slideView);
        slideContainer.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(slideContainer);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel slidePanel = new JPanel(new BorderLayout());
        slidePanel.setBackground(Color.WHITE);
        slidePanel.add(scrollPane, BorderLayout.CENTER);
        slidePanel.putClientProperty("directSlide", slide);

        tabbedPane.addTab(slide.getNodeName(), slidePanel);

        int index = tabbedPane.indexOfComponent(slidePanel);
        tabbedPane.setTabComponentAt(index, new JLabel("  " + slide.getNodeName() + "  "));
    }

    // Update koji zatvara sve Tabove prezentacija kada se izbrise Projekat kome pripadaju
    public void closeAllProjectTabs(Project project) {
        for (int i = tabbedPane.getTabCount() - 1; i >= 0; i--) {
            Component component = tabbedPane.getComponentAt(i);

            if (component instanceof JPanel panel) {
                Object tabPresentation = panel.getClientProperty("presentation");
                Object tabDirectSlide = panel.getClientProperty("directSlide");

                if (tabPresentation instanceof Presentation pres) {
                    if (pres.getParent() == project) {
                        tabbedPane.removeTabAt(i);
                    }
                } else if (tabDirectSlide instanceof Slide slide) {
                    if (slide.getParent() == project) {
                        tabbedPane.removeTabAt(i);
                    }
                }
            }
        }

        if(openProject == project) {
            openProject = null;
        }
    }

    public void closePresentationTab(Presentation presentation) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component component = tabbedPane.getComponentAt(i);

            if (component instanceof JPanel panel) {
                Object tabPresentation = panel.getClientProperty("presentation");
                if (tabPresentation == presentation) {
                    tabbedPane.removeTabAt(i);
                    break;
                }
            }
        }
    }

    public void closeDirectSlideTab(Slide slide) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component component = tabbedPane.getComponentAt(i);

            if (component instanceof JPanel panel) {
                Object tabDirectSlide = panel.getClientProperty("directSlide");
                if (tabDirectSlide == slide) {
                    tabbedPane.removeTabAt(i);
                    break;
                }
            }
        }
    }

    public void renamePresentationTab(Presentation presentation, String newName) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component component = tabbedPane.getComponentAt(i);

            if (component instanceof JPanel panel) {
                Object tabPresentation = panel.getClientProperty("presentation");
                if (tabPresentation == presentation) {
                    Component tabComponent = tabbedPane.getTabComponentAt(i);
                    if (tabComponent instanceof JLabel label) {
                        label.setText(newName);
                    }
                    break;
                }
            }
        }
    }

    public void renameDirectSlideTab(Slide slide, String newName) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component component = tabbedPane.getComponentAt(i);

            if (component instanceof JPanel panel) {
                Object tabDirectSlide = panel.getClientProperty("directSlide");
                if (tabDirectSlide == slide) {
                    Component tabComponent = tabbedPane.getTabComponentAt(i);
                    if (tabComponent instanceof JLabel label) {
                        label.setText("  " + newName + "  ");
                    }
                    break;
                }
            }
        }
    }

    public void clearAllTabs() {
        tabbedPane.removeAll();
    }

    public Presentation getCurrentPresentation() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex >= 0) {
            Component component = tabbedPane.getComponentAt(selectedIndex);
            if (component instanceof JPanel panel) {
                return (Presentation) panel.getClientProperty("presentation");
            }
        }
        return null;
    }

    public boolean isProjectOpen(Project project) {
        return openProject == project;
    }

    public void setCurrentlyOpenProject(Project project) {
        this.openProject = project;
    }

    public Project getCurrentlyOpenProject() {
        return openProject;
    }

    public int getSelectedTabIndex() {
        return tabbedPane.getSelectedIndex();
    }

    @Override
    public void update(Message message) {

    }

    public void setWindowModeManager(WindowModeManager windowModeManager) {
        this.windowModeManager = windowModeManager;
    }
}