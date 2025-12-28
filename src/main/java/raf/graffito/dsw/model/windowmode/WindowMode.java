package raf.graffito.dsw.model.windowmode;

import java.awt.*;
import java.util.List;

public abstract class WindowMode {

    protected List<WindowScaler> scalers;

    public WindowMode(List<WindowScaler> scalers) {
        this.scalers = scalers;
    }

    public abstract void applyMode();

    protected abstract Dimension getTargetDimension();

    protected abstract double getScaleFactor();

    protected boolean isFullscreen() {
        return false;
    }

    public String getModeName() {
        return this.getClass().getSimpleName().replace("Mode", "");
    }

    protected void executeScaling() {
        Dimension targetDimension = getTargetDimension();
        double scaleFactor = getScaleFactor();
        boolean fullscreen = isFullscreen();

        for (WindowScaler scaler : scalers) {
            scaler.applyScaling(targetDimension, scaleFactor, fullscreen);
        }
    }
}
