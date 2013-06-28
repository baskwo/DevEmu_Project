package org.devemu.events;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author Blackrush
 */
public class ReflectEventDispatcherStrategy extends EventDispatcherStrategy {
    // <event, subscriber, invokers>
    private final Table<Class<?>, Class<?>, Set<InvokerInterface>> cache = HashBasedTable.create();

    public static class Exception extends RuntimeException {
		private static final long serialVersionUID = 2788892830677395343L;
		public Exception(Method method, String message, Throwable cause) {
            super(method.toGenericString() + " can't be subscribed because : " + message, cause);
        }
        public Exception(Method method, String message) {
            this(method, message, null);
        }
    }

    @Override
    public void doDispatch(Object event, Object subscriber) {
        Set<InvokerInterface> invokers = cache.get(event.getClass(), subscriber.getClass());
        if (invokers == null) return;

        for (InvokerInterface invoker : invokers) {
            invoker.invoke(event, subscriber);
        }
    }

    @Override
    public void onSubscribed(Object subscriber) {
        Class<?> subscriberClass = subscriber.getClass();

        for (Method method : subscriberClass.getDeclaredMethods()) {
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if (annotation == null) continue;

            Class<?>[] targets;
            if (annotation.value().length > 0) {
                targets = annotation.value();
            } else {
                targets = method.getParameterTypes();
            }

            for (Class<?> target : targets) {
                Set<InvokerInterface> invokers = cache.get(target, subscriberClass);

                if (invokers == null) {
                    invokers = Sets.newHashSet();
                    cache.put(target, subscriberClass, invokers);
                }

                InvokerInterface invoker = buildInvoker(target, method);

                invokers.add(invoker);
            }
        }
    }

    protected InvokerInterface buildInvoker(Class<?> target, Method method) {
        if (Modifier.isAbstract(target.getModifiers()) || Modifier.isInterface(target.getModifiers())) {
            throw new Exception(method, "targets must be concrete types");
        }

        Class<?>[] parameters = method.getParameterTypes();

        if (parameters.length > 1) {
            throw new Exception(method, "it musts have only one parameter");
        }

        if (!parameters[0].isAssignableFrom(target)) {
            throw new Exception(method, "argument " + target.getName() + " must be a subclass of " + parameters[0].getName());
        }

        return new ReflectMethodInvoker(method);
    }
}
