package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.ActionManager;
import raf.graffito.dsw.gui.swing.jtree.GraffTreeCellRenderer;
import raf.graffito.dsw.gui.swing.jtree.GraffTreeImplementation;
import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.observer.Message;
import raf.graffito.dsw.model.observer.MessageGenerator;
import raf.graffito.dsw.model.observer.Subscriber;
import raf.graffito.dsw.model.repository.GraffRepository;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance;
    private ActionManager actionManager;

    private ProjectView projectView;
    private PresentationView presentationView;

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
        Toolkit kit = Toolkit.getDefaultToolkit();
        int screenHeight = 600;
        int screenWidth = 1000;
        Dimension startSize = new Dimension(screenWidth, screenHeight);
        setSize(startSize);
        setMinimumSize(startSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Graffito");
    }

    public void finishInitialization(GraffRepository repository, MessageGenerator messageGenerator) {

        Workspace workspace = repository.getWorkspace();

        projectView = new ProjectView();
        presentationView = new PresentationView();

        GraffTreeImplementation graffTree = new GraffTreeImplementation();
        actionManager = new ActionManager(graffTree, this, this, workspace);

        messageGenerator.addSubscriber(projectView);
        messageGenerator.addSubscriber(presentationView);

        MyMenuBar menu = new MyMenuBar(actionManager);
        setJMenuBar(menu);

        MyToolBar toolBar = new MyToolBar(actionManager);
        add(toolBar, BorderLayout.NORTH);

        JTree tree = graffTree.generateTree(workspace);
        tree.setCellRenderer(new GraffTreeCellRenderer());
        tree.setEditable(false);

        JScrollPane treeScroll = new JScrollPane(tree);
        treeScroll.setPreferredSize(new Dimension(250, getHeight()));

        JScrollPane infoScroll = new JScrollPane(projectView);
        infoScroll.setPreferredSize(new Dimension(250, getHeight()));

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                presentationView, infoScroll);
        rightSplitPane.setResizeWeight(1.0);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                treeScroll, rightSplitPane);
        mainSplitPane.setDividerLocation(250);
        mainSplitPane.setResizeWeight(0);

        mainSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            int dividerLocation = mainSplitPane.getDividerLocation();
            int maxDivider = 250;
            if (dividerLocation > maxDivider) {
                SwingUtilities.invokeLater(() -> mainSplitPane.setDividerLocation(maxDivider));
            }
        });

        rightSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            int dividerLocation = rightSplitPane.getDividerLocation();
            int minDivider = rightSplitPane.getWidth() - 250;
            if (dividerLocation < minDivider) {
                SwingUtilities.invokeLater(() -> rightSplitPane.setDividerLocation(minDivider));
            }
        });

        add(mainSplitPane, BorderLayout.CENTER);
    }

    @Override
    public void update(Message message) {
        SwingUtilities.invokeLater(() -> {
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

                }
            }
        });
    }

    @Override
    public void updateSelected(Project project, Presentation presentation) {
        projectView.updateProjectInfo(project, presentation);
    }

    public JTabbedPane getTabbedPane() {
        return presentationView.getTabbedPane();
    }

    public ProjectView getProjectView() {
        return projectView;
    }

    public PresentationView getPresentationView() {
        return presentationView;
    }
}