package raf.graffito.dsw.model.windowmode;

import java.awt.*;
import java.util.List;

public class FullscreenMode extends WindowMode {

    private static final double FULLSCREEN_SCALE_MULTIPLIER = 1.5;

    public FullscreenMode(List<WindowScaler> scalers) {
        super(scalers);
    }

    @Override
    public void applyMode() {
        executeScaling();
    }

    @Override
    protected Dimension getTargetDimension() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }

    @Override
    protected double getScaleFactor() {
        return FULLSCREEN_SCALE_MULTIPLIER;
    }

    @Override
    protected boolean isFullscreen() {
        return true;
    }
}
