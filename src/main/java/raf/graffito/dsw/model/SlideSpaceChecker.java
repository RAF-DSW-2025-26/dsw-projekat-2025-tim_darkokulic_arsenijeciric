package raf.graffito.dsw.model;

import java.util.List;

public interface SlideSpaceChecker {

    boolean hasAvailableSpace(List<SlideElement> elements, int slideWidth, int slideHeight, double newElementArea);

    double getOccupiedSpacePercentage(List<SlideElement> elements, int slideWidth, int slideHeight);

    String getStrategyName();
}
