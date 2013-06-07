package org.devemu.events;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Blackrush
 */
public final class EventType {
    public static final EventType ALL = create("event-type.all.key");

    public static EventType create(String key) {
        return new EventType(key);
    }

    public static EventType create(Class<? extends EventInterface> eventClass) {
        return create(eventClass.getName());
    }

    private final String key;

    public EventType(final String key) {
        this.key = checkNotNull(key);
    }

    public String getKey() {
        return key;
    }
}
