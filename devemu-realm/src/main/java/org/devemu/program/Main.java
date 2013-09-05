package org.devemu.program;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.devemu.events.SimpleEventDispatcher;
import org.devemu.inject.ConfigModule;
import org.devemu.inject.DispatcherModule;
import org.devemu.inject.HelperModule;
import org.devemu.inject.JarModuleInstaller;
import org.devemu.inject.MessageModule;
import org.devemu.inject.ModuleInstaller;
import org.devemu.inject.QueueModule;
import org.devemu.inject.ServiceManager;
import org.devemu.network.event.LoginEventDispatcherStrategy;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.message.InterMessageFactory;
import org.devemu.network.message.MessageFactory;
import org.devemu.queue.QueueListener;
import org.devemu.sql.SimpleSqlInitiator;
import org.devemu.sql.entity.helper.AccountHelper;
import org.devemu.sql.entity.helper.BanHelper;
import org.devemu.utils.Pair;
import org.devemu.utils.Stopwatch;
import org.devemu.utils.queue.LoginQueueListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static Injector inject;
    private static Config config;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
        final ClassLoader loader = ClassLoader.getSystemClassLoader();
        config = ConfigFactory.parseFileAnySyntax(new File("./devemu-realm"));
        
        inject = Guice.createInjector(
                ConfigModule.of(config),
                JarModuleInstaller.from(config.getString("devemu.mods.dir")),
                ModuleInstaller.of(loader, config.getConfig("devemu.mods")),
                MessageModule.of(new MessageFactory(), new InterMessageFactory(),loader),
                DispatcherModule.of(SimpleEventDispatcher.create(new LoginEventDispatcherStrategy()),loader),
                QueueModule.of(new Pair<String,Class<? extends QueueListener>>("login",LoginQueueListener.class)),
                new JpaPersistModule("devemu-realm"),
                new HelperModule() {
					@Override
					public void initialize() {
						add(AccountHelper.class);
						add(BanHelper.class);
					}
                }
        );
        
        SimpleSqlInitiator initiator = new SimpleSqlInitiator();
        inject.injectMembers(initiator);
        initiator.initiate();
        
        final ServiceManager services = ServiceManager.of(loader, inject, config.getConfig("devemu.mods"));
        ClientFactory.init();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                services.stop();
            }
        }));
        try (Stopwatch ignored = Stopwatch.start(log, "successfully started in {} milliseconds")) {
            services.start();
        }
    }

	public static String getConfigValue(String field) {
		return config.getString(field);
	}
	
	public static <T> T getInstanceFromInjector(Class<T> instance) {
		return inject.getInstance(instance);
	}
	
	public static void log(String message,Class<?> logging) {
		LoggerFactory.getLogger(logging).debug(message);
	}
}
