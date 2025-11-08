package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {

    public HelpDialog(Frame parent) {
        super(parent, "Help", true);
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        JTextArea helpText = new JTextArea(
                "Uputsvo za korišćenje programa:\n\n" +
                        "1. Dupli klik dodeljuje boju projektu\n" +
                        "2. Desni klik otvara projekat bez dodavanja boje\n" +
                        "3. Edit Project dugme može da se koristi samo na izabranom projektu\n" +
                        "4. Workspace ne može da se obriše\n" +
                        "5. Projekat mora da sadrži ili slajd ili prezentaciju\n" +
                        "6. Kada se Add dugme iskoristi na projektu, birate da li želite slajd ili prezentaciju"
        );
        helpText.setEditable(false);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setCaretPosition(0);
        helpText.setHighlighter(null);
        helpText.setFocusable(false);
        helpText.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JScrollPane scrollPane = new JScrollPane(helpText);
        scrollPane.setPreferredSize(new Dimension(400, 180));

        JButton closeButton = new JButton("Close");
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        buttonPanel.add(closeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }
}
