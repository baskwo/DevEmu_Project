package org.devemu.inject;

import java.io.IOException;

import org.devemu.events.EventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.ClassPath;
import com.google.inject.AbstractModule;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.propagate;

public class DispatcherModule extends AbstractModule {
	
	public static DispatcherModule of(EventDispatcher dispatcher,ClassLoader loader) {
		return new DispatcherModule(dispatcher,loader);
	}
	
	private static final Logger log = LoggerFactory.getLogger(DispatcherModule.class);
	
	private EventDispatcher dispatcher;
	private ClassPath path;
	
	public DispatcherModule(EventDispatcher dispatcher,ClassLoader loader) {
		this.dispatcher = checkNotNull(dispatcher);
		try {
			path = ClassPath.from(loader);
		} catch (IOException e) {
			throw propagate(e);
		}
	}

	@Override
	protected void configure() {
		bind(EventDispatcher.class).toInstance(dispatcher);
		init(path);
	}
	
	private void init(ClassPath classPath) {
		for (ClassPath.ResourceInfo info : classPath.getTopLevelClassesRecursive("org.devemu.network.event.handler")) {
			if (info instanceof ClassPath.ClassInfo) {
            	ClassPath.ClassInfo classInfo = (ClassPath.ClassInfo) info;
                Class<?> eventClass = classInfo.load();
                try {
                	Object event = eventClass.newInstance();
                	requestInjection(event);
					dispatcher.subscribe(event);
					log.debug("{} subscribe to dispatcher",eventClass.getName());
				} catch (InstantiationException
						| IllegalAccessException e) {
					log.error("Can't instanciate {}", eventClass.getName());
					throw propagate(e);
				}
            }
		}
	}
}
