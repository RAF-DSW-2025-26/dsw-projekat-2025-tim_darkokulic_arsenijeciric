package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.ActionManager;
import raf.graffito.dsw.controller.GraffTreeCellRenderer;
import raf.graffito.dsw.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MainFrame extends JFrame implements Subscriber{

    // Buduća polja za sve komponente view-a na glavnom prozoru

    private static MainFrame instance;
    private ActionManager actionManager;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JLabel projectNameLabel = new JLabel("No project selected");
    private JLabel presentationNameLabel = new JLabel("No presentation selected");
    private JLabel authorLabel = new JLabel("No author");

    private MainFrame() {
        initialize();
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    private void initialize() {
        Toolkit kit = Toolkit.getDefaultToolkit();           // Toolkit omogućava interakciju sa platformom
        Dimension screenSize = kit.getScreenSize();         // Veličina ekrana
        int screenHeight = screenSize.height / 2;
        int screenWidth = screenSize.width / 2;
        Dimension startSize = new Dimension(screenWidth, screenHeight);
        setSize(startSize);
        setMinimumSize(startSize);
        setLocationRelativeTo(null);                        // Centriranje prozora na ekranu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // Zatvaranje aplikacije pri zatvaranju prozora
        setTitle("Graffito");                               // Naslov prozora


        GraffTreeImplementation graffTree = new GraffTreeImplementation();
        actionManager = new ActionManager(graffTree, this, this);

        MyMenuBar menu = new MyMenuBar(actionManager);      // Kreiranje menija
        setJMenuBar(menu);                                  // Postavljanje menija na prozor

        MyToolBar toolBar = new MyToolBar(actionManager);   // Kreiranje toolbar-a
        add(toolBar, BorderLayout.NORTH);                   // Postavljanje toolbar-a na vrh prozora

        Workspace workspace = new Workspace("My Workspace");

        JTree tree = graffTree.generateTree(workspace);
        tree.setCellRenderer(new GraffTreeCellRenderer());  // renderer koji postavlja custom ikonice za sve komponente
        tree.setEditable(false);

        JScrollPane treeScroll = new JScrollPane(tree);
        treeScroll.setPreferredSize(new Dimension(250, getHeight()));

        /// Info panel na desnoj strani
        JPanel infoPanel = createInfoPanel();
        JScrollPane infoScroll = new JScrollPane(infoPanel);
        infoScroll.setPreferredSize(new Dimension(250, getHeight()));

        /// Srednji panel sa tabovima
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(tabbedPane, BorderLayout.CENTER);

        /// Desni split pane (tabs + info panel)
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerPanel, infoScroll);
        rightSplitPane.setResizeWeight(1.0);

        /// Levi split pane (tree + desni deo)
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, rightSplitPane);
        mainSplitPane.setDividerLocation(250);
        mainSplitPane.setResizeWeight(0);

        /// Ogranicenje za levi panel
        mainSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            int dividerLocation = mainSplitPane.getDividerLocation();
            int maxDivider = 250;
            if (dividerLocation > maxDivider) {
                SwingUtilities.invokeLater(() -> mainSplitPane.setDividerLocation(maxDivider));
            }
        });

        rightSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            int dividerLocation = rightSplitPane.getDividerLocation();
            int minDivider = rightSplitPane.getWidth() - 250; // maximum width of info panel = 300
            if (dividerLocation < minDivider) {
                SwingUtilities.invokeLater(() -> rightSplitPane.setDividerLocation(minDivider));
            }
        });

        add(mainSplitPane, BorderLayout.CENTER);
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("Project Information")
        ));
        panel.add(Box.createVerticalStrut(5));
        panel.add(createInfoRow("Project:", projectNameLabel));
        panel.add(Box.createVerticalStrut(5));
        panel.add(createSeparator());
        panel.add(Box.createVerticalStrut(5));
        panel.add(createInfoRow("Presentation:", presentationNameLabel));
        panel.add(Box.createVerticalStrut(5));
        panel.add(createSeparator());
        panel.add(Box.createVerticalStrut(5));
        panel.add(createInfoRow("Author:", authorLabel));
        panel.add(Box.createVerticalStrut(5));
        panel.add(createSeparator());

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createInfoRow(String labelText, JLabel valueLabel) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.BOLD));

        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.add(label);
        row.add(valueLabel);

        return row;
    }

    // linija izmedju redova na desnom pane-u
    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return separator;
    }

    @Override
    public void update(Message message) {

        SwingUtilities.invokeLater(() -> {

            /// prozor za error poruke
            switch (message.getType()) {
                case "GREŠKA" -> JOptionPane.showMessageDialog(
                        this,
                        message.getContent(),
                        "Greška",
                        JOptionPane.ERROR_MESSAGE
                );
                case "OBAVEŠTENJE" -> JOptionPane.showMessageDialog(
                        this,
                        message.getContent(),
                        "Informacija",
                        JOptionPane.INFORMATION_MESSAGE
                );
                default -> {
                    // Ignoriše upozorenja i druge tipove
                }
            }
        });
    }

    @Override
    public void updateSelected(Project project, Presentation presentation) {
        projectNameLabel.setText(project != null ? project.getNodeName() : "");
        authorLabel.setText(project != null ? project.getAuthor() : "");
        presentationNameLabel.setText(presentation != null ? presentation.getNodeName() : "");
    }


    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
