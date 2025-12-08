package raf.graffito.dsw.gui.swing.view;

import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Project;
import raf.graffito.dsw.model.Slide;
import raf.graffito.dsw.model.observer.Message;
import raf.graffito.dsw.model.observer.Subscriber;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SlideView extends JPanel implements Subscriber {

    public static final int SLIDE_WIDTH = 800;
    public static final int SLIDE_HEIGHT = 600;

    private Slide slide;
   // private List<SlideElement> elements;

    public SlideView(Slide slide) {
        this.slide = slide;
        initialize();
    }

    private void initialize() {
        setPreferredSize(new Dimension(SLIDE_WIDTH, SLIDE_HEIGHT));
        setMinimumSize(new Dimension(SLIDE_WIDTH, SLIDE_HEIGHT));
        setMaximumSize(new Dimension(SLIDE_WIDTH, SLIDE_HEIGHT));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        setLayout(new BorderLayout());
        JLabel placeholder = new JLabel("Slide: " + slide.getTitle(), SwingConstants.CENTER);
        placeholder.setFont(new Font("Arial", Font.BOLD, 16));
        placeholder.setForeground(Color.GRAY);
        add(placeholder, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }

    public Slide getSlide() {
        return slide;
    }

    public void setSlide(Slide slide) {
        this.slide = slide;
    }


    @Override
    public void update(Message message) {

    }

    @Override
    public void updateSelected(Project project, Presentation presentation) {
        Subscriber.super.updateSelected(project, presentation);
    }
}