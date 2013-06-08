package org.devemu.program;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.devemu.inject.ConfigModule;
import org.devemu.inject.JarModuleInstaller;
import org.devemu.inject.ModuleInstaller;
import org.devemu.inject.ServiceManager;
import org.devemu.utils.Stopwatch;
import org.devemu.utils.config.ConfigEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
        final ClassLoader loader = ClassLoader.getSystemClassLoader();
        final Config config = ConfigFactory.parseFileAnySyntax(new File("./devemu-realm"));

        final Injector inject = Guice.createInjector(
                ConfigModule.of(config),
                JarModuleInstaller.from(config.getString("devemu.mods.dir")),
                ModuleInstaller.of(loader, config.getConfig("devemu.mods"))
        );

        final ServiceManager services = ServiceManager.of(loader, inject, config.getConfig("devemu.mods"));

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                services.stop();
            }
        }));

        try (Stopwatch ignored = Stopwatch.start(log, "successfully started in {} milliseconds")) {
            services.start();
        }
    }

	public static Object getConfigValue(ConfigEnum arg1) {
        return null; // todo
	}
}
