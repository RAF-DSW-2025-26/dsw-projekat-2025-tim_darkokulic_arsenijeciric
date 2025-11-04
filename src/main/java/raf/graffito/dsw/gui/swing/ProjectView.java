package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.model.Message;
import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Project;
import raf.graffito.dsw.model.Subscriber;

import javax.swing.*;
import java.awt.*;


public class ProjectView extends JPanel implements Subscriber {

    private JLabel projectNameLabel;
    private JLabel presentationNameLabel;
    private JLabel authorLabel;

    public ProjectView() {
        initialize();
    }

    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("Project Information")
        ));

        projectNameLabel = new JLabel("No project selected");
        presentationNameLabel = new JLabel("No presentation selected");
        authorLabel = new JLabel("No author");

        add(Box.createVerticalStrut(5));
        add(createInfoRow("Project:", projectNameLabel));
        add(Box.createVerticalStrut(5));
        add(createSeparator());
        add(Box.createVerticalStrut(5));
        add(createInfoRow("Presentation:", presentationNameLabel));
        add(Box.createVerticalStrut(5));
        add(createSeparator());
        add(Box.createVerticalStrut(5));
        add(createInfoRow("Author:", authorLabel));
        add(Box.createVerticalStrut(5));
        add(createSeparator());

        add(Box.createVerticalGlue());
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

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return separator;
    }

    public void updateProjectInfo(Project project, Presentation presentation) {
        projectNameLabel.setText(project != null ? project.getNodeName() : "No project selected");
        authorLabel.setText(project != null ? project.getAuthor() : "No author");
        presentationNameLabel.setText(presentation != null ? presentation.getNodeName() : "No presentation selected");
    }

    public void clearProjectInfo() {
        projectNameLabel.setText("No project selected");
        presentationNameLabel.setText("No presentation selected");
        authorLabel.setText("No author");
    }

    @Override
    public void update(Message message) {
    }
}