package raf.graffito.dsw.model;

public interface Subscriber {
    void update(Message message);

    // prosirena funkcionalnost subscribera da bi mogli koristiti observer sablon za
    // update-ovanje desnog panela sa imenom autora, projekta i ostalog
    default void updateSelected(Project project, Presentation presentation) {}
}
