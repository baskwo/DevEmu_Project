package org.devemu.network.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.devemu.events.InvokerInterface;
import org.devemu.network.client.BaseClient;
import org.devemu.network.event.event.game.GameClientEvent;
import org.devemu.network.message.Message;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Throwables.propagate;

/**
 * @author Blackrush
 */
public class GameEventInvoker implements InvokerInterface {
	
	private final Method method;
    private final Class<?> messageClass;
	
	public GameEventInvoker(Method method) {
        checkArgument(method.getParameterTypes().length == 2,
                "%s must have 2 parameters : LoginClient and Message", method.toGenericString());
        checkArgument(BaseClient.class.isAssignableFrom(method.getParameterTypes()[0]),
                "%s must have in first parameter a BaseClient", method.toGenericString());
        checkArgument(Message.class.isAssignableFrom(method.getParameterTypes()[1]),
                "%s must have in second parameter a message", method.toGenericString());

        this.method = method;
        this.method.setAccessible(true);
        this.messageClass = method.getParameterTypes()[1];
    }

	@Override
	public Object invoke(Object o, Object subscriber) {
		checkArgument(o instanceof GameClientEvent, "event is not a ClientLoginEvent");

		GameClientEvent event = (GameClientEvent) o;

        if (!messageClass.isAssignableFrom(event.getMessage().getClass())) {
            return null;
        }
            try {
				return method.invoke(subscriber, event.getClient(), event.getMessage());
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw propagate(e);
			}
	}
}
