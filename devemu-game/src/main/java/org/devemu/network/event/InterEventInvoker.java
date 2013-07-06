package org.devemu.network.event;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Throwables.propagate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.devemu.events.InvokerInterface;
import org.devemu.network.client.ServerClient;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.message.InterMessage;

public class InterEventInvoker implements InvokerInterface {
	private final Method method;
    private final Class<?> messageClass;
	
	public InterEventInvoker(Method method) {
        checkArgument(method.getParameterTypes().length == 2,
                "%s must have 2 parameters : InterClient and Message", method.toGenericString());
        checkArgument(ServerClient.class.isAssignableFrom(method.getParameterTypes()[0]),
                "%s must have in first parameter a ServerClient", method.toGenericString());
        checkArgument(InterMessage.class.isAssignableFrom(method.getParameterTypes()[1]),
                "%s must have in second parameter a message", method.toGenericString());

        this.method = method;
        this.method.setAccessible(true);
        this.messageClass = method.getParameterTypes()[1];
    }

	@Override
	public Object invoke(Object o, Object subscriber) {
		checkArgument(o instanceof InterClientEvent, "event is not a InterClientEvent");

        InterClientEvent event = (InterClientEvent) o;

        if (!messageClass.isAssignableFrom(event.getMessage().getClass()))
            return null;
        try {
			return method.invoke(subscriber, event.getServer(), event.getMessage());
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw propagate(e);
		}
	}
}
