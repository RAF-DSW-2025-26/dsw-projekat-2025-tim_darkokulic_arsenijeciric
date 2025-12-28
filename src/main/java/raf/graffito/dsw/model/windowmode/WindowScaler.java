package raf.graffito.dsw.model.windowmode;

import java.awt.*;

public interface WindowScaler {
    void applyScaling(Dimension targetDimension, double scaleFactor, boolean isFullscreen);
}
