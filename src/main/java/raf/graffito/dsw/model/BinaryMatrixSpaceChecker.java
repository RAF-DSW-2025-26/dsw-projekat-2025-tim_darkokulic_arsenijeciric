package raf.graffito.dsw.model;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class BinaryMatrixSpaceChecker implements SlideSpaceChecker {

    private static final double MINIMUM_FREE_SPACE_PERCENTAGE = 20.0;
    private static final int SCALE = 5;

    @Override
    public boolean hasAvailableSpace(List<SlideElement> elements, int slideWidth, int slideHeight, double newElementArea) {
        double occupiedPercentage = getOccupiedSpacePercentage(elements, slideWidth, slideHeight);
        double freeSpacePercentage = 100.0 - occupiedPercentage;

        return freeSpacePercentage >= MINIMUM_FREE_SPACE_PERCENTAGE;
    }

    @Override
    public double getOccupiedSpacePercentage(List<SlideElement> elements, int slideWidth, int slideHeight) {
        int matrixWidth = (int) Math.ceil((double) slideWidth / SCALE);
        int matrixHeight = (int) Math.ceil((double) slideHeight / SCALE);

        boolean[][] occupiedMatrix = new boolean[matrixHeight][matrixWidth];

        for (SlideElement element : elements) {
            markElementInMatrix(element, occupiedMatrix, matrixWidth, matrixHeight);
        }

        int occupiedPixels = 0;
        int totalPixels = matrixWidth * matrixHeight;

        for (int y = 0; y < matrixHeight; y++) {
            for (int x = 0; x < matrixWidth; x++) {
                if (occupiedMatrix[y][x]) {
                    occupiedPixels++;
                }
            }
        }

        return ((double) occupiedPixels / totalPixels) * 100.0;
    }

    @Override
    public String getStrategyName() {
        return "Binary Matrix Method";
    }

    private void markElementInMatrix(SlideElement element, boolean[][] matrix, int matrixWidth, int matrixHeight) {
        Point position = element.getPosition();
        Dimension size = element.getSize();
        double rotation = Math.toRadians(element.getRotation());

        Rectangle2D bounds = getRotatedBounds(position, size, rotation);

        int minX = Math.max(0, (int) (bounds.getMinX() / SCALE));
        int maxX = Math.min(matrixWidth - 1, (int) (bounds.getMaxX() / SCALE));
        int minY = Math.max(0, (int) (bounds.getMinY() / SCALE));
        int maxY = Math.min(matrixHeight - 1, (int) (bounds.getMaxY() / SCALE));

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                matrix[y][x] = true;
            }
        }
    }

    private Rectangle2D getRotatedBounds(Point position, Dimension size, double rotation) {
        Rectangle2D rect = new Rectangle2D.Double(0, 0, size.width, size.height);

        AffineTransform transform = new AffineTransform();
        transform.rotate(rotation, size.width / 2.0, size.height / 2.0);

        Shape rotatedShape = transform.createTransformedShape(rect);
        Rectangle2D rotatedBounds = rotatedShape.getBounds2D();

        return new Rectangle2D.Double(
                position.x + rotatedBounds.getX(),
                position.y + rotatedBounds.getY(),
                rotatedBounds.getWidth(),
                rotatedBounds.getHeight()
        );
    }
}
