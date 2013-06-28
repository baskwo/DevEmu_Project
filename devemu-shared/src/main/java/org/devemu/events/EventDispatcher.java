package org.devemu.events;

/**
 * @author Blackrush
 */
public abstract class EventDispatcher {
    public abstract void dispatch(Object event);

    public abstract void subscribe(Object subscriber);
    public abstract void unsubscribe(Object subscriber);
}
