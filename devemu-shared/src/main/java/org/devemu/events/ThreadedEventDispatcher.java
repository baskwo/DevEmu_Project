package org.devemu.events;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Blackrush
 */
public final class ThreadedEventDispatcher extends EventDispatcher {
    private final EventDispatcherStrategy strategy;
    private final Executor worker;
    private final Set<Object> subscribers = Sets.newHashSet();

    private ThreadedEventDispatcher(EventDispatcherStrategy strategy, Executor worker) {
        this.strategy = checkNotNull(strategy);
        this.worker = checkNotNull(worker);
    }

    public static ThreadedEventDispatcher create(EventDispatcherStrategy strategy, Executor worker) {
        return new ThreadedEventDispatcher(strategy, worker);
    }

    public static ThreadedEventDispatcher create(EventDispatcherStrategy strategy) {
        return create(strategy, Executors.newSingleThreadExecutor());
    }

    @Override
    public void dispatch(final Object event) {
        worker.execute(new Runnable() {
            @Override
			public void run() {
                for (Object subscriber : subscribers) {
                    strategy.doDispatch(event, subscriber);
                }
            }
        });
    }

    @Override
    public void subscribe(final Object subscriber) {
        worker.execute(new Runnable() {
            @Override
			public void run() {
                subscribers.add(subscriber);
                strategy.onSubscribed(subscriber);
            }
        });
    }

    @Override
    public void unsubscribe(final Object subscriber) {
        worker.execute(new Runnable() {
            @Override
			public void run() {
                subscribers.remove(subscriber);
            }
        });
    }
}
