package org.devemu.inject;

import com.google.common.reflect.ClassPath;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.propagate;

/**
 * @author Blackrush
 */
public class ModuleInstaller extends AbstractModule {
    private static final Logger log = LoggerFactory.getLogger(ModuleInstaller.class);

    private final ClassPath classPath;
    private final Config config;

    private ModuleInstaller(ClassLoader loader, Config config) {
        try {
            this.classPath = ClassPath.from(loader);
        } catch (IOException e) {
            throw propagate(e);
        }

        this.config = checkNotNull(config);
    }

    public static ModuleInstaller of(ClassLoader loader, Config config) {
        return new ModuleInstaller(loader, config);
    }

    private Class<? extends Module> findModule(String moduleClassName) {
        for (ClassPath.ResourceInfo info : classPath.getResources()) {
            if (info instanceof ClassPath.ClassInfo) {
                ClassPath.ClassInfo classInfo = (ClassPath.ClassInfo) info;

                if (classInfo.getName().equals(moduleClassName)) {
                    Class<?> moduleClass = classInfo.load();

                    if (!moduleClass.isAssignableFrom(Module.class)) {
                        //noinspection unchecked
                        return (Class<? extends Module>) moduleClass;
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected void configure() {
        for (String moduleClassName : config.getStringList("modules")) {
            Class<? extends Module> moduleClass = findModule(moduleClassName);
            if (moduleClass == null) continue;

            Module module;
            try {
                module = moduleClass.newInstance();
            } catch (InstantiationException|IllegalAccessException e) {
                addError(e);
                continue;
            }

            install(module);

            log.debug("module {} installed", moduleClassName);
        }
    }
}
