package raf.graffito.dsw.model.windowmode;

import raf.graffito.dsw.gui.swing.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class WindowModeManager {

    private WindowMode currentMode;
    private final WindowMode normalMode;
    private final WindowMode fullscreenMode;
    private final WindowMode smallMode;

    public WindowModeManager(MainFrame mainFrame) {
        List<WindowScaler> scalers = new ArrayList<>();
        scalers.add(new FrameScaler(mainFrame));
        scalers.add(new SlideScaler(mainFrame));

        this.normalMode = new NormalMode(scalers);
        this.fullscreenMode = new FullscreenMode(scalers);
        this.smallMode = new SmallMode(scalers);

        this.currentMode = normalMode;
    }

    public void setMode(String modeName) {
        switch (modeName.toLowerCase()) {
            case "normal" -> currentMode = normalMode;
            case "fullscreen" -> currentMode = fullscreenMode;
            case "small" -> currentMode = smallMode;
            default -> throw new IllegalArgumentException("Unknown mode: " + modeName);
        }
        currentMode.applyMode();
    }

    public WindowMode getCurrentMode() {
        return currentMode;
    }

    public String getCurrentModeName() {
        return currentMode.getModeName();
    }

    public double getCurrentScaleFactor() {
        return currentMode.getScaleFactor();
    }
}
