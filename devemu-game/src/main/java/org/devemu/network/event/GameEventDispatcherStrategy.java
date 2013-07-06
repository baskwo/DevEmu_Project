package org.devemu.network.event;

import java.lang.reflect.Method;

import org.devemu.events.InvokerInterface;
import org.devemu.events.ReflectEventDispatcherStrategy;
import org.devemu.network.event.event.game.GameClientEvent;
import org.devemu.network.event.event.inter.InterClientEvent;

/**
 * @author Blackrush
 */
public class GameEventDispatcherStrategy extends
		ReflectEventDispatcherStrategy {
	
	@Override
    protected InvokerInterface buildInvoker(Class<?> target, Method method) {
        if (GameClientEvent.class.equals(target))
            return new GameEventInvoker(method);
        else if(InterClientEvent.class.equals(target))
        	return new InterEventInvoker(method);

        return super.buildInvoker(target, method);
    }
}
