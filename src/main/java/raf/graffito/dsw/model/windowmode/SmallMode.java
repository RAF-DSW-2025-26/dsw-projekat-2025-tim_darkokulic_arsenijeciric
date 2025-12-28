package raf.graffito.dsw.model.windowmode;

import java.awt.*;
import java.util.List;

public class SmallMode extends WindowMode {

    private static final int SMALL_WIDTH = 950;
    private static final int SMALL_HEIGHT = 550;
    private static final double SMALL_SCALE = 0.5;

    public SmallMode(List<WindowScaler> scalers) {
        super(scalers);
    }

    @Override
    public void applyMode() {
        executeScaling();
    }

    @Override
    protected Dimension getTargetDimension() {
        return new Dimension(SMALL_WIDTH, SMALL_HEIGHT);
    }

    @Override
    protected double getScaleFactor() {
        return SMALL_SCALE;
    }
}
