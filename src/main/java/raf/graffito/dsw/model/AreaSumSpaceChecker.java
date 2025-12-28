package raf.graffito.dsw.model;

import java.util.List;

public class AreaSumSpaceChecker implements SlideSpaceChecker {

    private static final double MINIMUM_FREE_SPACE_PERCENTAGE = 20.0;

    @Override
    public boolean hasAvailableSpace(List<SlideElement> elements, int slideWidth, int slideHeight, double newElementArea) {
        double totalSlideArea = slideWidth * slideHeight;
        double occupiedArea = calculateTotalOccupiedArea(elements);
        double areaAfterAdding = occupiedArea + newElementArea;

        double freeSpacePercentage = ((totalSlideArea - areaAfterAdding) / totalSlideArea) * 100.0;

        return freeSpacePercentage >= MINIMUM_FREE_SPACE_PERCENTAGE;
    }

    @Override
    public double getOccupiedSpacePercentage(List<SlideElement> elements, int slideWidth, int slideHeight) {
        double totalSlideArea = slideWidth * slideHeight;
        double occupiedArea = calculateTotalOccupiedArea(elements);

        double percentage = (occupiedArea / totalSlideArea) * 100.0;
        return Math.min(percentage, 100.0);
    }

    @Override
    public String getStrategyName() {
        return "Area Sum Method";
    }

    private double calculateTotalOccupiedArea(List<SlideElement> elements) {
        double totalArea = 0.0;

        for (SlideElement element : elements) {
            int width = element.getSize().width;
            int height = element.getSize().height;
            totalArea += width * height;
        }

        return totalArea;
    }
}
