package raf.graffito.dsw.model.observer;

import raf.graffito.dsw.model.Presentation;
import raf.graffito.dsw.model.Project;

public interface Subscriber {
    void update(Message message);

    // prosirena funkcionalnost subscribera da bi mogli koristiti observer sablon za
    // update-ovanje desnog panela sa imenom autora, projekta i ostalog
    default void updateSelected(Project project, Presentation presentation) {}

    default void onSlideEvent(SlideEvent event) {}
}
