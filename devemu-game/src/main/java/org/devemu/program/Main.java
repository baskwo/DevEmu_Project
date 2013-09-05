package org.devemu.program;

import java.io.File;

import org.devemu.events.SimpleEventDispatcher;
import org.devemu.file.FileProvider;
import org.devemu.file.provider.StatsFileProvider;
import org.devemu.inject.ConfigModule;
import org.devemu.inject.DispatcherModule;
import org.devemu.inject.HelperModule;
import org.devemu.inject.JarModuleInstaller;
import org.devemu.inject.MessageModule;
import org.devemu.inject.ModuleInstaller;
import org.devemu.inject.QueueModule;
import org.devemu.inject.ServiceManager;
import org.devemu.inject.StatsModule;
import org.devemu.network.event.GameEventDispatcherStrategy;
import org.devemu.network.message.InterMessageFactory;
import org.devemu.network.message.MessageFactory;
import org.devemu.queue.QueueListener;
import org.devemu.sql.SimpleSqlInitiator;
import org.devemu.sql.entity.helper.AccountHelper;
import org.devemu.sql.entity.helper.BanHelper;
import org.devemu.sql.entity.helper.ChannelHelper;
import org.devemu.sql.entity.helper.MapsHelper;
import org.devemu.sql.entity.helper.PlayerHelper;
import org.devemu.sql.entity.helper.StatsHelper;
import org.devemu.utils.Pair;
import org.devemu.utils.Stopwatch;
import org.devemu.utils.enums.ServerPop;
import org.devemu.utils.enums.ServerState;
import org.devemu.utils.queue.SelectionQueueListener;
import org.devemu.utils.queue.TransfertQueueListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static Injector inject;
    private static Config config;
	
	private static ServerState state = ServerState.OFFLINE;
	private static ServerPop population = ServerPop.FULL;
	private static int guid = 0;
	private static boolean allowNoSubscribe = true;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		final ClassLoader loader = ClassLoader.getSystemClassLoader();
        config = ConfigFactory.parseFileAnySyntax(new File("./devemu-game"));

        inject = Guice.createInjector(
                ConfigModule.of(config),
                JarModuleInstaller.from(config.getString("devemu.mods.dir")),
                ModuleInstaller.of(loader, config.getConfig("devemu.mods")),
                MessageModule.of(new MessageFactory(), new InterMessageFactory(), loader),
                DispatcherModule.of(SimpleEventDispatcher.create(new GameEventDispatcherStrategy()),loader),
                QueueModule.of(new Pair<String,Class<? extends QueueListener>>("transfert",TransfertQueueListener.class),
                			new Pair<String,Class<? extends QueueListener>>("selection",SelectionQueueListener.class)),
                StatsModule.of(new Pair<String,FileProvider>("stats",StatsFileProvider.from("./static/stats.data"))),
                new JpaPersistModule("devemu-game"),
                new HelperModule() {
					@Override
					public void initialize() {
						add(AccountHelper.class);
						add(BanHelper.class);
						add(ChannelHelper.class);
						add(MapsHelper.class);
						add(PlayerHelper.class);
						add(StatsHelper.class);
					}
				}
        );
        
        SimpleSqlInitiator initiator = new SimpleSqlInitiator();
        inject.injectMembers(initiator);
        initiator.initiate();

        final ServiceManager services = ServiceManager.of(loader, inject, config.getConfig("devemu.mods"));
        
        setGuid(config.getInt("devemu.options.game.guid"));
        setAllowNoSubscribe(config.getBoolean("devemu.options.game.allowNoSubscribe"));
        setState(ServerState.ONLINE);
        setPopulation(ServerPop.RECOMMENDED);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
            	services.stop();
            }
        }));

        try (Stopwatch ignored = Stopwatch.start(log, "successfully started in {} milliseconds")) {
            services.start();
        }
	}
	
	public static <T> T getInstanceFromInjector(Class<T> instance) {
		return inject.getInstance(instance);
	}
	
	public static void log(String message,Class<?> logging) {
		LoggerFactory.getLogger(logging).debug(message);
	}

	public static String getConfigValue(String field) {
		return config.getString(field);
	}

	public static ServerState getState() {
		return state;
	}

	public static void setState(ServerState state) {
		Main.state = state;
	}

	public static ServerPop getPopulation() {
		return population;
	}

	public static void setPopulation(ServerPop population) {
		Main.population = population;
	}

	public static int getGuid() {
		return guid;
	}

	public static void setGuid(int guid) {
		Main.guid = guid;
	}

	public static boolean isAllowNoSubscribe() {
		return allowNoSubscribe;
	}

	public static void setAllowNoSubscribe(boolean allowNoSubscribe) {
		Main.allowNoSubscribe = allowNoSubscribe;
	}
}
