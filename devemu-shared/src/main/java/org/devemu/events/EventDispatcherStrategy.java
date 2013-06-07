package org.devemu.events;

/**
 * @author Blackrush
 */
public abstract class EventDispatcherStrategy {
    public abstract void subscribed(Object subscriber);
    public abstract void dispatch(EventInterface event, Object subscriber);
}
