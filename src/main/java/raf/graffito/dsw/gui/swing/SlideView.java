package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.command.UndoRedoManager;
import raf.graffito.dsw.controller.state.StateManager;
import raf.graffito.dsw.model.*;
import raf.graffito.dsw.model.observer.Message;
import raf.graffito.dsw.model.observer.Subscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SlideView extends JPanel implements Subscriber {

    public static final int SLIDE_WIDTH = 800;
    public static final int SLIDE_HEIGHT = 600;
    public static final int SLIDE_MARGIN = 50;

    private Slide slide;
    private StateManager stateManager;
    private UndoRedoManager undoRedoManager;
    private List<SlideElement> selectedElements;
    private double zoomFactor = 1.0;
    private Rectangle selectionRectangle;

    public SlideView(Slide slide) {
        this.slide = slide;
        this.stateManager = new StateManager();
        this.undoRedoManager = new UndoRedoManager();
        this.selectedElements = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        int totalWidth = (int) ((SLIDE_WIDTH + 2 * SLIDE_MARGIN) * zoomFactor);
        int totalHeight = (int) ((SLIDE_HEIGHT + 2 * SLIDE_MARGIN) * zoomFactor);
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setBackground(new Color(186, 186, 186));
        setBorder(null);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MouseEvent convertedEvent = convertMouseEvent(e);
                stateManager.mousePressed(convertedEvent, SlideView.this);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                MouseEvent convertedEvent = convertMouseEvent(e);
                stateManager.mouseReleased(convertedEvent, SlideView.this);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                MouseEvent convertedEvent = convertMouseEvent(e);
                stateManager.mouseClicked(convertedEvent, SlideView.this);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                MouseEvent convertedEvent = convertMouseEvent(e);
                stateManager.mouseDragged(convertedEvent, SlideView.this);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                MouseEvent convertedEvent = convertMouseEvent(e);
                stateManager.mouseMoved(convertedEvent, SlideView.this);
            }
        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                stateManager.mouseWheelMoved(e.getWheelRotation(), e, SlideView.this);
            }
        });

        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.scale(zoomFactor, zoomFactor);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(SLIDE_MARGIN, SLIDE_MARGIN, SLIDE_WIDTH, SLIDE_HEIGHT);

        g2d.setColor(new Color(200, 200, 200));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(SLIDE_MARGIN, SLIDE_MARGIN, SLIDE_WIDTH, SLIDE_HEIGHT);

        g2d.translate(SLIDE_MARGIN, SLIDE_MARGIN);

        for (SlideElement element : slide.getElements()) {
            if (element instanceof ImageElement image) {
                new raf.graffito.dsw.gui.swing.painter.ImagePainter(image).paint(g2d);
            } else if (element instanceof LabelElement label) {
                new raf.graffito.dsw.gui.swing.painter.LabelPainter(label).paint(g2d);
            } else if (element instanceof LogoElement logo) {
                new raf.graffito.dsw.gui.swing.painter.LogoPainter(logo).paint(g2d);
            }

            if (selectedElements.contains(element)) {
                g2d.setColor(new Color(0, 120, 215, 100));
                g2d.setStroke(new BasicStroke(2));
                Rectangle bounds = getRotatedBounds(element);
                g2d.draw(bounds);

                int handleSize = 8;
                g2d.setColor(new Color(0, 120, 215));
                g2d.fillRect(bounds.x - handleSize / 2, bounds.y - handleSize / 2, handleSize, handleSize);
                g2d.fillRect(bounds.x + bounds.width - handleSize / 2, bounds.y - handleSize / 2, handleSize, handleSize);
                g2d.fillRect(bounds.x - handleSize / 2, bounds.y + bounds.height - handleSize / 2, handleSize, handleSize);
                g2d.fillRect(bounds.x + bounds.width - handleSize / 2, bounds.y + bounds.height - handleSize / 2, handleSize, handleSize);
            }
        }

        if (selectionRectangle != null) {
            g2d.setColor(new Color(0, 120, 215, 50));
            g2d.fill(selectionRectangle);
            g2d.setColor(new Color(0, 120, 215));
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(selectionRectangle);
        }

        g2d.dispose();
    }


    public Slide getSlide() {
        return slide;
    }

    public void setSlide(Slide slide) {
        this.slide = slide;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public UndoRedoManager getUndoRedoManager() {
        return undoRedoManager;
    }

    public List<SlideElement> getSelectedElements() {
        return selectedElements;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
        int totalWidth = (int) ((SLIDE_WIDTH + 2 * SLIDE_MARGIN) * zoomFactor);
        int totalHeight = (int) ((SLIDE_HEIGHT + 2 * SLIDE_MARGIN) * zoomFactor);
        setPreferredSize(new Dimension(totalWidth, totalHeight));
    }

    public Point convertToSlideCoordinates(Point componentPoint) {
        int slideX = (int) ((componentPoint.x / zoomFactor) - SLIDE_MARGIN);
        int slideY = (int) ((componentPoint.y / zoomFactor) - SLIDE_MARGIN);
        return new Point(slideX, slideY);
    }


    private MouseEvent convertMouseEvent(MouseEvent e) {
        Point slideCoords = convertToSlideCoordinates(e.getPoint());
        return new MouseEvent(
            e.getComponent(),
            e.getID(),
            e.getWhen(),
            e.getModifiersEx(),
            slideCoords.x,
            slideCoords.y,
            e.getClickCount(),
            e.isPopupTrigger(),
            e.getButton()
        );
    }

    public Rectangle getSelectionRectangle() {
        return selectionRectangle;
    }

    public void setSelectionRectangle(Rectangle selectionRectangle) {
        this.selectionRectangle = selectionRectangle;
    }

    private Rectangle getRotatedBounds(SlideElement element) {
        if (element.getRotation() == 0) {
            return new Rectangle(element.getPosition(), element.getSize());
        }

        int x = element.getPosition().x;
        int y = element.getPosition().y;
        int w = element.getSize().width;
        int h = element.getSize().height;

        double centerX = x + w / 2.0;
        double centerY = y + h / 2.0;

        Point[] corners = {
            new Point(x, y),
            new Point(x + w, y),
            new Point(x, y + h),
            new Point(x + w, y + h)
        };

        double angle = element.getRotation();
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point corner : corners) {
            double dx = corner.x - centerX;
            double dy = corner.y - centerY;

            double rotatedX = dx * cos - dy * sin;
            double rotatedY = dx * sin + dy * cos;

            int finalX = (int) Math.round(rotatedX + centerX);
            int finalY = (int) Math.round(rotatedY + centerY);

            minX = Math.min(minX, finalX);
            maxX = Math.max(maxX, finalX);
            minY = Math.min(minY, finalY);
            maxY = Math.max(maxY, finalY);
        }

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public void update(Message message) {
        repaint();
    }

    @Override
    public void updateSelected(Project project, Presentation presentation) {
        Subscriber.super.updateSelected(project, presentation);
    }
}