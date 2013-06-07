package org.devemu.events;

/**
 * every implementation must have a {@code public static final} field named {@code TYPE} of type {@link EventType}
 *
 * @author Blackrush
 * @see EventDispatcher
 * @see EventType
 */
public interface EventInterface {
    /**
     * describes event
     * @return non-null {@link EventType}
     */
    EventType getType();
}
