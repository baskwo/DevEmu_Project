package org.devemu.network.event;

import java.lang.reflect.Method;

import org.devemu.events.InvokerInterface;
import org.devemu.events.ReflectEventDispatcherStrategy;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.event.event.login.ClientLoginEvent;

/**
 * @author Blackrush
 */
public class LoginEventDispatcherStrategy extends
		ReflectEventDispatcherStrategy {
	
	@Override
    protected InvokerInterface buildInvoker(Class<?> target, Method method) {
        if (ClientLoginEvent.class.equals(target))
            return new LoginEventInvoker(method);
        else if(InterClientEvent.class.equals(target))
        	return new InterEventInvoker(method);

        return super.buildInvoker(target, method);
    }
}
