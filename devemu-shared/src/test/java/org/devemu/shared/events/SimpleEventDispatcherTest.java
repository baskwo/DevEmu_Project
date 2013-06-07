package org.devemu.shared.events;

import org.devemu.events.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Blackrush
 */
@RunWith(JUnit4.class)
public class SimpleEventDispatcherTest {
    public static class Event implements EventInterface {
        public static final EventType TYPE = EventType.create(Event.class);

        @Override
        public EventType getType() {
            return TYPE;
        }
    }

    public static class OtherEvent implements  EventInterface {
        public static final EventType TYPE = EventType.create(OtherEvent.class);

        @Override
        public EventType getType() {
            return TYPE;
        }
    }

    public static class Handler {
        boolean handled1, handled2;

        @Subscribe
        public void handleEvent(Event event) {
            handled1 = true;
        }

        @Subscribe
        public void handleOtherEvent(OtherEvent event) {
            handled2 = true;
        }
    }

    private final ReflectiveEventDispatcherStrategy strategy = new ReflectiveEventDispatcherStrategy();

    @Test
    public void dispatchAll() {
        SimpleEventDispatcher dispatcher = new SimpleEventDispatcher(strategy);
        Handler handler = new Handler();

        dispatcher.subscribe(handler);

        dispatcher.dispatch(new Event());
        assertThat(handler.handled1, is(true));
        assertThat(handler.handled2, is(false));

        dispatcher.dispatch(new OtherEvent());
        assertThat(handler.handled1, is(true));
        assertThat(handler.handled2, is(true));
    }

    @Test
    public void dispatchEvent() {
        SimpleEventDispatcher dispatcher = new SimpleEventDispatcher(strategy);
        Handler handler = new Handler();

        dispatcher.subscribe(handler, Event.TYPE);

        dispatcher.dispatch(new Event());
        assertThat(handler.handled1, is(true));
        assertThat(handler.handled2, is(false));

        dispatcher.dispatch(new OtherEvent());
        assertThat(handler.handled1, is(true));
        assertThat(handler.handled2, is(false));
    }
}
