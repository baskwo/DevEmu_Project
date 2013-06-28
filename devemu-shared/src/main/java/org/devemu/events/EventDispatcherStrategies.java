package org.devemu.events;

/**
 * @author Blackrush
 */
public final class EventDispatcherStrategies {
    private EventDispatcherStrategies() {}

    public static ReflectEventDispatcherStrategy reflect() {
        return new ReflectEventDispatcherStrategy();
    }
}
