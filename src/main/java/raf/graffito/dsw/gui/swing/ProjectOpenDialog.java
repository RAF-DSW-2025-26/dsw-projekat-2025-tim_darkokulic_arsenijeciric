package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;

public class ProjectOpenDialog extends JDialog {

    private boolean confirmed = false;
    private String choice = null; // "presentation" ili "slide"

    public ProjectOpenDialog(Frame parent) {
        super(parent, "Open Project", true);
        initializeDialog();
    }

    private void initializeDialog() {
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(Color.WHITE);
        messagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel("<html><center>Projekat mora sadržati barem jednu prezentaciju ili slajd<br>" +
                "Izaberite šta želite da dodate:</center></html>");

        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messagePanel.add(messageLabel);

        messagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(messagePanel);

        mainPanel.add(Box.createVerticalStrut(10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        GradientButton presentationButton = new GradientButton("Presentation", Color.WHITE, new Color(186, 186, 186));
        presentationButton.setPreferredSize(new Dimension(150, 25));
        presentationButton.addActionListener(e -> {
            choice = "presentation";
            confirmed = true;
            dispose();
        });

        GradientButton slideButton = new GradientButton("Slide", Color.WHITE, new Color(186, 186, 186));
        slideButton.setPreferredSize(new Dimension(150, 25));
        slideButton.addActionListener(e -> {
            choice = "slide";
            confirmed = true;
            dispose();
        });

        buttonPanel.add(presentationButton);
        buttonPanel.add(slideButton);
        mainPanel.add(buttonPanel);

        mainPanel.add(Box.createVerticalStrut(10));

        GradientButton cancelButton = new GradientButton("Cancel", Color.WHITE, new Color(186, 186, 186));
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        mainPanel.add(cancelButton);

        add(mainPanel);
        pack();
        setLocationRelativeTo(getParent());
    }


    public boolean isConfirmed() {
        return confirmed;
    }

    public String getChoice() {
        return choice;
    }
}
