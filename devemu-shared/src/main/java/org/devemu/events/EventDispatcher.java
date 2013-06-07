package org.devemu.events;

import java.util.Arrays;

/**
 * @author Blackrush
 */
public abstract class EventDispatcher {
    public abstract void dispatch(EventInterface event);

    public abstract void subscribe(Object subscriber, Iterable<EventType> types);
    public abstract void unsubscribe(Object subscriber);

    public void subscribe(Object subscriber, EventType... types) {
        subscribe(subscriber, Arrays.asList(types));
    }

    public void subscribe(Object subscriber) {
        subscribe(subscriber, EventType.ALL);
    }
}
