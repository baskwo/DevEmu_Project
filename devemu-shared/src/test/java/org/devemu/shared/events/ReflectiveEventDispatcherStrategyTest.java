package org.devemu.shared.events;

import com.google.common.collect.Table;
import org.devemu.events.EventInterface;
import org.devemu.events.EventType;
import org.devemu.events.ReflectiveEventDispatcherStrategy;
import org.devemu.events.Subscribe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Blackrush
 */
@RunWith(JUnit4.class)
public class ReflectiveEventDispatcherStrategyTest {
    public static class Event implements EventInterface {
        public static final EventType TYPE = EventType.create(Event.class);

        @Override
        public EventType getType() {
            return TYPE;
        }
    }

    public static class Handler {
        boolean handled;

        @Subscribe
        public void handleEvent(Event event) {
            handled = true;
        }
    }

    public static class Tested extends ReflectiveEventDispatcherStrategy {
        public Table<Class<?>, EventType, Method> getCache() {
            return cache;
        }
    }

    @Test
    public void testSubscribed() {
        Tested strategy = new Tested();
        strategy.subscribed(new Handler());

        assertThat(strategy.getCache().get(Handler.class, Event.TYPE), notNullValue());
    }

    @Test
    public void testDispatch() {
        Handler handler = new Handler();
        Tested strategy = new Tested();

        strategy.subscribed(handler); // force cache
        strategy.dispatch(new Event(), handler);

        assertThat(handler.handled, is(true));
    }
}
