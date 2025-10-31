package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.model.Project;

import javax.swing.*;
import java.awt.*;

public class EditProjectDialog extends JDialog {

    private JTextField nameField;
    private JTextField authorField;
    private boolean confirmed = false;

    public EditProjectDialog(JFrame parent, Project project) {
        super(parent, "Edit Project Details", true);
        setResizable(false);
        setPreferredSize(new Dimension(350, 150));

        nameField = new JTextField(project.getNodeName(), 20);
        authorField = new JTextField(project.getAuthor(), 20);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Project Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });

        cancelButton.addActionListener(e -> setVisible(false));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getProjectName() {
        return nameField.getText().trim();
    }

    public String getProjectAuthor() {
        return authorField.getText().trim();
    }
}
