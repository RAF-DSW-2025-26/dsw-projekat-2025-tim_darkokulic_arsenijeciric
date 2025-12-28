package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.observer.Message;
import raf.graffito.dsw.model.observer.Subscriber;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectView extends JPanel implements Subscriber {

    private JLabel projectNameLabel;
    private JLabel presentationNameLabel;
    private JLabel authorLabel;
    private JPanel imageListPanel;
    private JScrollPane imageScroll;
    private List<ImageProxy> images;
    private int selectedImageIndex = -1;
    private List<JLabel> imageThumbnails;

    public ProjectView() {
        this.imageThumbnails = new ArrayList<>();
        this.images = new ArrayList<>();
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

        JButton addImageButton = new JButton("Add Image");
        addImageButton.addActionListener(e -> addImages());

        imageListPanel = new JPanel();
        imageListPanel.setLayout(new BoxLayout(imageListPanel, BoxLayout.Y_AXIS));

        refreshImageList();

        imageScroll = new JScrollPane(imageListPanel);
        imageScroll.setPreferredSize(new Dimension(200, 300));
        imageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        imageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addImageButton);

        imageSection.add(buttonPanel, BorderLayout.NORTH);
        imageSection.add(imageScroll, BorderLayout.CENTER);

        add(infoPanel, BorderLayout.NORTH);
        add(imageSection, BorderLayout.CENTER);
    }

    private void loadTestImages() {
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
                    if (img != null) {
                        String tempPath = "resource://" + path;
                        images.add(RealImage.fromBufferedImage(tempPath, img));
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to load image: " + path);
            }
        }
    }

    private void addImages() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Images", "jpg", "jpeg", "png", "gif", "bmp"
        ));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            for (File file : files) {
                images.add(new ProxyImage(file.getAbsolutePath()));
            }
            refreshImageList();
        }
    }

    private void refreshImageList() {
        imageListPanel.removeAll();
        imageThumbnails.clear();

        for (int i = 0; i < images.size(); i++) {
            JLabel thumbnail = createThumbnail(i);
            imageThumbnails.add(thumbnail);
            imageListPanel.add(thumbnail);
            imageListPanel.add(Box.createVerticalStrut(5));
        }

        imageListPanel.revalidate();
        imageListPanel.repaint();
    }

    private JLabel createThumbnail(int index) {
        ImageProxy imageProxy = images.get(index);
        BufferedImage thumbnail = imageProxy.getThumbnail();
        
        ImageIcon icon = new ImageIcon(thumbnail);
        JLabel thumbnailLabel = new JLabel(icon);
        thumbnailLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        thumbnailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        thumbnailLabel.setPreferredSize(new Dimension(100, 100));
        thumbnailLabel.setMaximumSize(new Dimension(100, 100));
        thumbnailLabel.setMinimumSize(new Dimension(100, 100));

        thumbnailLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selectImage(index);
            }
        });

        return thumbnailLabel;
    }

    private void selectImage(int index) {
        if (selectedImageIndex >= 0 && selectedImageIndex < imageThumbnails.size()) {
            imageThumbnails.get(selectedImageIndex).setBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2)
            );
        }

        selectedImageIndex = index;
        if (selectedImageIndex >= 0 && selectedImageIndex < imageThumbnails.size()) {
            imageThumbnails.get(selectedImageIndex).setBorder(
                BorderFactory.createLineBorder(Color.BLUE, 3)
            );
        }
    }

    public BufferedImage getSelectedImage() {
        if (selectedImageIndex >= 0 && selectedImageIndex < images.size()) {
            return images.get(selectedImageIndex).getImage();
        }
        return null;
    }

    public int getSelectedImageIndex() {
        return selectedImageIndex;
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