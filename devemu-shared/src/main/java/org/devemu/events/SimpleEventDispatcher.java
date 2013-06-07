package org.devemu.events;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

/**
 * @author Blackrush
 */
public class SimpleEventDispatcher extends EventDispatcher {
    private final Multimap<EventType, Object> subscribers = HashMultimap.create();
    private final EventDispatcherStrategy strategy;

    public SimpleEventDispatcher(EventDispatcherStrategy strategy) {
        this.strategy = checkNotNull(strategy);
    }

    @Override
    public void subscribe(Object subscriber, Iterable<EventType> types) {
        for (EventType type : types) {
            subscribers.put(type, subscriber);
        }
        subscribed(subscriber);
    }

    @Override
    public void unsubscribe(Object subscriber) {
        for (EventType type : subscribers.keys()) {
            subscribers.remove(type, subscriber);
        }
    }

    @Override
    public void dispatch(EventInterface event) {
        for (EventType type : asList(event.getType(), EventType.ALL)) {
            for (Object subscriber : subscribers.get(type)) {
                dispatch(event, subscriber);
            }
        }
    }

    protected void subscribed(Object subscriber) {
        strategy.subscribed(subscriber);
    }

    protected void dispatch(EventInterface event, Object subscriber) {
        strategy.dispatch(event, subscriber);
    }
}
