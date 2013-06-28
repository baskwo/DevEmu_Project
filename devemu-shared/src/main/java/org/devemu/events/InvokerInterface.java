package org.devemu.events;

/**
 * @author Blackrush
 */
public interface InvokerInterface {
    Object invoke(Object event, Object subscriber);
}
