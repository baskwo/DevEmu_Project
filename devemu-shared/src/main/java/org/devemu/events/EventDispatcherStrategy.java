package org.devemu.events;

/**
 * @author Blackrush
 */
public abstract class EventDispatcherStrategy {
    public abstract void onSubscribed(Object subscriber);
    public abstract void doDispatch(Object event, Object subscriber);
}
