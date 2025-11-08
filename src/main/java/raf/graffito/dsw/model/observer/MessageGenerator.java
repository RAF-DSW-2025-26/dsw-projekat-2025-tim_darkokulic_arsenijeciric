package raf.graffito.dsw.model.observer;

import java.util.ArrayList;
import java.util.List;

public class MessageGenerator implements Publisher {

    private final List<Subscriber> subscribers = new ArrayList<Subscriber>();

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(String content, String type) {
        Message message = new Message(content, type);
        for (Subscriber subscriber : subscribers) {
            subscriber.update(message);
        }
    }
}
