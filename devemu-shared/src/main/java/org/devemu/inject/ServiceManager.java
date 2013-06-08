package org.devemu.inject;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.propagate;

/**
 * @author Blackrush
 */
public class ServiceManager implements Startable {
    private static final Logger log = LoggerFactory.getLogger(ServiceManager.class);

    private final ClassPath classPath;
    private final Injector inject;
    private final Config config;

    private final List<Startable> services = Lists.newArrayList();

    private ServiceManager(ClassLoader loader, Injector inject, Config config) {
        try {
            this.classPath = ClassPath.from(loader);
        } catch (IOException e) {
            throw propagate(e);
        }

        this.inject = checkNotNull(inject);
        this.config = checkNotNull(config);
    }

    public static ServiceManager of(ClassLoader loader, Injector inject, Config config) {
        return new ServiceManager(loader, inject, config);
    }

    private Class<? extends Startable> findService(String serviceClassName) {
        for (ClassPath.ResourceInfo info : classPath.getResources()) {
            if (info instanceof ClassPath.ClassInfo) {
                ClassPath.ClassInfo classInfo = (ClassPath.ClassInfo) info;

                if (classInfo.getName().equals(serviceClassName)) {
                    Class<?> serviceClass = classInfo.load();

                    if (!serviceClass.isAssignableFrom(Startable.class)) {
                        //noinspection unchecked
                        return (Class<? extends Startable>) serviceClass;
                    }
                }
            }
        }

        return null;
    }

    private void init() {
        for (String serviceClassName : config.getStringList("services")) {
            Class<? extends Startable> serviceClass = findService(serviceClassName);
            if (serviceClass == null) continue;

            Startable service = inject.getInstance(serviceClass);
            services.add(service);

            log.debug("service {} installed", serviceClassName);
        }
    }

    @Override
    public void start() {
        init();

        for (Startable service : services) {
            try {
                service.start();
            } catch (Exception e) {
                log.error("can't start service " + service.getClass().getName(), e);
            }
        }
    }

    @Override
    public void stop() {
        for (int i = services.size() - 1; i >= 0; i--) {
            try {
                services.get(i).stop();
            } catch (Exception e) {
                log.error(services.getClass().getName() + " stop has failed", e);
            }
        }
    }
}
