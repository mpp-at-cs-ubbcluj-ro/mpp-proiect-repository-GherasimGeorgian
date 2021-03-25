package triatlon.utils.observer;

import triatlon.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}