package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.observer.Message;
import raf.graffito.dsw.model.observer.Subscriber;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectView extends JPanel implements Subscriber {

    private JLabel projectNameLabel;
    private JLabel presentationNameLabel;
    private JLabel authorLabel;
    private JPanel imageListPanel;
    private List<BufferedImage> testImages;

    public ProjectView() {
        loadTestImages();
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("Project Information")
        ));

        projectNameLabel = new JLabel("No project selected");
        presentationNameLabel = new JLabel("No presentation selected");
        authorLabel = new JLabel("No author");

        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(createInfoRow("Project:", projectNameLabel));
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(createSeparator());
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(createInfoRow("Presentation:", presentationNameLabel));
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(createSeparator());
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(createInfoRow("Author:", authorLabel));
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(createSeparator());

        JPanel imageSection = new JPanel(new BorderLayout());
        imageSection.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("Available Images")
        ));

        imageListPanel = new JPanel();
        imageListPanel.setLayout(new BoxLayout(imageListPanel, BoxLayout.Y_AXIS));

        for (BufferedImage img : testImages) {
            JLabel thumbnail = createThumbnail(img);
            imageListPanel.add(thumbnail);
            imageListPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane imageScroll = new JScrollPane(imageListPanel);
        imageScroll.setPreferredSize(new Dimension(200, 300));
        imageSection.add(imageScroll, BorderLayout.CENTER);

        add(infoPanel, BorderLayout.NORTH);
        add(imageSection, BorderLayout.CENTER);
    }

    private void loadTestImages() {
        testImages = new ArrayList<>();
        String[] imagePaths = {
                "/images/darko.jpg",
                "/images/arsenije.jpg",
                "/images/test3.png"
        };

        for (String path : imagePaths) {
            try {
                var stream = getClass().getResourceAsStream(path);
                if (stream != null) {
                    BufferedImage img = ImageIO.read(stream);
                    testImages.add(img);
                }
            } catch (IOException e) {
                System.err.println("Failed to load image: " + path);
            }
        }

        if (testImages.isEmpty()) {
            testImages.add(createPlaceholderImage(Color.RED));
            testImages.add(createPlaceholderImage(Color.GREEN));
            testImages.add(createPlaceholderImage(Color.BLUE));
        }
    }

    private BufferedImage createPlaceholderImage(Color color) {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 100, 100);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Test", 30, 55);
        g2d.dispose();
        return img;
    }

    private JLabel createThumbnail(BufferedImage img) {

        Image scaled = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);

        JLabel thumbnail = new JLabel(icon);
        thumbnail.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        thumbnail.setAlignmentX(Component.CENTER_ALIGNMENT);

        return thumbnail;
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