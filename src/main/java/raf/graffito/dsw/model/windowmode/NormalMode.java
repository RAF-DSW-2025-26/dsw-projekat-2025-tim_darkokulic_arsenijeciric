package raf.graffito.dsw.model.windowmode;

import java.awt.*;
import java.util.List;

public class NormalMode extends WindowMode {

    private static final int NORMAL_WIDTH = 1200;
    private static final int NORMAL_HEIGHT = 700;
    private static final double NORMAL_SCALE = 1.0;

    public NormalMode(List<WindowScaler> scalers) {
        super(scalers);
    }

    @Override
    public void applyMode() {
        executeScaling();
    }

    @Override
    protected Dimension getTargetDimension() {
        return new Dimension(NORMAL_WIDTH, NORMAL_HEIGHT);
    }

    @Override
    protected double getScaleFactor() {
        return NORMAL_SCALE;
    }
}
