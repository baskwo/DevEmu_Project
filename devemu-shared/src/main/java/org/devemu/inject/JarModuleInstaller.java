package org.devemu.inject;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarFile;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * load all jar from a directory and install module specified in manifest by key 'Module-Class'
 *
 * @author Blackrush
 */
public class JarModuleInstaller extends AbstractModule {
    private static final Logger log = LoggerFactory.getLogger(JarModuleInstaller.class);
    private static final String MODULE_CLASS_ATTR = "Module-Class";

    private final String path;
    private final FilenameFilter fileFilter;

    private JarModuleInstaller(String path, FilenameFilter fileFilter) {
        this.path = checkNotNull(path);
        this.fileFilter = fileFilter;
    }

    public static JarModuleInstaller from(String path, FilenameFilter fileFilter) {
        return new JarModuleInstaller(path, fileFilter);
    }

    public static JarModuleInstaller from(String path) {
        return from(path, null);
    }

    private void getNamesAndUrls(File directory, List<String> names, List<URL> urls) {
        for (File file : directory.listFiles(fileFilter)) {
            try {
                JarFile jarfile = new JarFile(file);

                String moduleName = jarfile.getManifest().getMainAttributes().getValue(MODULE_CLASS_ATTR);
                URL moduleUrl = file.toURI().toURL();

                names.add(moduleName);
                urls.add(moduleUrl);
            } catch (Throwable t) {
                addError(t);
            }
        }
    }

    private Collection<Module> loadModules(URLClassLoader classLoader, Collection<String> names) {
        List<Module> modules = Lists.newArrayList();

        for (String moduleName : names) {
            try {
                Class<?> moduleClass = classLoader.loadClass(moduleName);
                if (Module.class.isAssignableFrom(moduleClass)) {
                    Module module = (Module) moduleClass.newInstance();
                    modules.add(module);
                } else {
                    addError("Class \"%s\" is not a module !", moduleClass.getCanonicalName());
                }
            } catch (Exception e) {
                addError(e);
            }
        }

        return modules;
    }

    private Collection<Module> loadModules(File directory) {
        List<String> names = Lists.newArrayList();
        List<URL> urls = Lists.newArrayList();

        getNamesAndUrls(directory, names, urls);

        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));

        return loadModules(classLoader, names);
    }

    @Override
    protected void configure() {
        File directory = new File(path);
        if (!directory.exists()) {
            addError("unknown %s directory", path);
        } else if (!directory.isDirectory()) {
            addError("directory %s is not a directory", path);
        } else {
            for (Module module : loadModules(directory)) {
                install(module);

                log.info("{} loaded", module.getClass().getCanonicalName());
            }
        }
    }
}