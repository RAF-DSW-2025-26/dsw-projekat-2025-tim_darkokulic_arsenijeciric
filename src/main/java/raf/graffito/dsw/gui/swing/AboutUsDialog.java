package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;

public class AboutUsDialog extends JDialog{

    public AboutUsDialog() {

        setTitle("About Us");
        setResizable(false); // fiksirana veličina prozora
        setIconImage(new ImageIcon(getClass().getResource("/images/about_us.png")).getImage());

        Icon arsenijeIcon = loadAndScaleImage("/images/arsenije.jpg", 160, 200);
        Icon darkoIcon = loadAndScaleImage("/images/darko.jpg", 160, 200);

        JLabel arsenijeLabel = new JLabel(arsenijeIcon);
        JLabel darkoLabel = new JLabel(darkoIcon);

        JLabel arsenijeName = new JLabel("Arsenije Ćirić", SwingConstants.CENTER);
        JLabel arsenijeIndex = new JLabel("61/2025 SI", SwingConstants.CENTER);
        JLabel darkoName = new JLabel("Darko Kulić", SwingConstants.CENTER);
        JLabel darkoIndex = new JLabel("77/2025 RN", SwingConstants.CENTER);

        JPanel arsenijeCaption = new JPanel(new GridLayout(2,1));
        JPanel darkoCaption = new JPanel(new GridLayout(2,1));
        arsenijeCaption.add(arsenijeName);
        arsenijeCaption.add(arsenijeIndex);
        darkoCaption.add(darkoName);
        darkoCaption.add(darkoIndex);

        arsenijeCaption.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        darkoCaption.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel imagePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        JPanel arsenijePanel = new JPanel(new BorderLayout());
        arsenijePanel.add(arsenijeLabel, BorderLayout.CENTER);
        arsenijePanel.add(arsenijeCaption, BorderLayout.SOUTH);

        JPanel darkoPanel = new JPanel(new BorderLayout());
        darkoPanel.add(darkoLabel, BorderLayout.CENTER);
        darkoPanel.add(darkoCaption, BorderLayout.SOUTH);

        imagePanel.add(arsenijePanel);
        imagePanel.add(darkoPanel);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose()); // Dugme OK samo briše prozor (dijalog)

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(imagePanel, BorderLayout.NORTH);
        content.add(okButton, BorderLayout.SOUTH);

        setContentPane(content);
        pack();
        setLocationRelativeTo(null);
    }

    // Pomoćna funkcija koja smanjuje sliku da bude odgovarajuće veličine
    private Icon loadAndScaleImage(String path, int width, int height) {

        java.net.URL imageURL = getClass().getResource(path);

        ImageIcon originalIcon = new ImageIcon(imageURL);
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
