package org.devemu.events;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static com.google.common.base.Throwables.propagate;

/**
 * @author Blackrush
 */
public class ReflectiveEventDispatcherStrategy extends EventDispatcherStrategy {
    protected final Table<Class<?>, EventType, Method> cache = HashBasedTable.create();

    @SuppressWarnings("unchecked")
    @Override
    public void subscribed(Object subscriber) {
        if (cache.containsRow(subscriber.getClass())) return;

        Class<?> subscriberClass = subscriber.getClass();

        for (Method method : subscriberClass.getMethods()) {
            if (!method.isAnnotationPresent(Subscribe.class)) continue;

            Type[] params = method.getGenericParameterTypes();

            if (params.length != 1) {
                throw new IllegalStateException(String.format("%s is not a valid event handler : " +
                        "there must be only one parameter",
                        method.toGenericString()));
            }

            TypeToken param = TypeToken.of(params[0]);

            if (param.isAssignableFrom(TypeToken.of(EventInterface.class))) {
                throw new IllegalStateException(String.format("%s is not a valid event handler : " +
                        "first parameter must be an EventInterface instance",
                        method.toGenericString()));
            }

            EventType type = findEventType(param);

            if (type == null) {
                throw new IllegalStateException(String.format("%s is not a valid event handler : " +
                        "%s is not describable (can't find an EventType)",
                        method.toGenericString(), param.toString()));
            }

            method.setAccessible(true);

            cache.put(subscriberClass, type, method);
        }
    }

    private EventType findEventType(TypeToken token) {
        try {
            return (EventType) token.getRawType().getField("TYPE").get(null);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public void dispatch(EventInterface event, Object subscriber) {
        Method method = cache.get(subscriber.getClass(), event.getType());

        if (method != null) {
            try {
                method.invoke(subscriber, event);
            } catch (IllegalAccessException ignored) {
            } catch (InvocationTargetException e) {
                throw propagate(e.getTargetException());
            }
        }
    }
}
