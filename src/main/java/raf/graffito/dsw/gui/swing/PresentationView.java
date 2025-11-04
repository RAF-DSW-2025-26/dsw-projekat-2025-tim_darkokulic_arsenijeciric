package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.model.Message;
import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Subscriber;

import javax.swing.*;
import java.awt.*;

public class PresentationView extends JPanel implements Subscriber {

    private JTabbedPane tabbedPane;

    public PresentationView() {
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void addPresentationTab(Presentation presentation, Color projectColor) {
        JPanel slidePanel = new JPanel();
        slidePanel.setBackground(Color.WHITE);
        slidePanel.putClientProperty("presentation", presentation);

        tabbedPane.addTab(presentation.getNodeName(), slidePanel);

        int index = tabbedPane.indexOfComponent(slidePanel);
        JLabel tabLabel = new JLabel(presentation.getNodeName());
        tabLabel.setOpaque(true);
        tabLabel.setBackground(projectColor);
        tabLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        tabbedPane.setTabComponentAt(index, tabLabel);
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

    public int getSelectedTabIndex() {
        return tabbedPane.getSelectedIndex();
    }

    @Override
    public void update(Message message) {

    }
}