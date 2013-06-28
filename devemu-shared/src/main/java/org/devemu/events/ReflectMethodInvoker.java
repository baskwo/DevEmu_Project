package org.devemu.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.propagate;

/**
 * @author Blackrush
 */
public class ReflectMethodInvoker implements InvokerInterface {
    private final Method method;

    public ReflectMethodInvoker(Method method) {
        this.method = checkNotNull(method);
        this.method.setAccessible(true);
    }

    @Override
    public Object invoke(Object event, Object subscriber) {
        try {
            return method.invoke(subscriber, event);
        } catch (IllegalAccessException ignored) {
            throw propagate(ignored); // shouldn't happend
        } catch (InvocationTargetException e) {
            throw propagate(e.getTargetException());
        }
    }
}
