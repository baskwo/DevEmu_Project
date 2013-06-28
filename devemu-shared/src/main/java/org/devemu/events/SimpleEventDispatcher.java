package org.devemu.events;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author Blackrush
 */
public final class SimpleEventDispatcher extends EventDispatcher {
    private final EventDispatcherStrategy strategy;
    private final Set<Object> subscribers = Sets.newHashSet();

    private SimpleEventDispatcher(EventDispatcherStrategy strategy) {
        this.strategy = strategy;
    }

    public static SimpleEventDispatcher create(EventDispatcherStrategy strategy) {
        return new SimpleEventDispatcher(strategy);
    }

    @Override
    public void dispatch(Object event) {
        for (Object subscriber : subscribers) {
            strategy.doDispatch(event, subscriber);
        }
    }

    @Override
    public void subscribe(Object subscriber) {
        subscribers.add(subscriber);
        strategy.onSubscribed(subscriber);
    }

    @Override
    public void unsubscribe(Object subscriber) {
        subscribers.remove(subscriber);
    }
}
