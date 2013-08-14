package org.devemu.program;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
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
import org.devemu.sql.SqlService;
import org.devemu.sql.SqlServiceImpl;
import org.devemu.sql.entity.mapper.AccountMapper;
import org.devemu.sql.entity.mapper.BanMapper;
import org.devemu.sql.entity.mapper.PlayerMapper;
import org.devemu.utils.Pair;
import org.devemu.utils.Stopwatch;
import org.devemu.utils.helper.AccountHelper;
import org.devemu.utils.helper.BanHelper;
import org.devemu.utils.queue.LoginQueueListener;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.dbcp.BasicDataSourceProvider;
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
                new MyBatisModule() {

                    @Override
                    protected void initialize() {
                        bindDataSourceProviderType(BasicDataSourceProvider.class);
                        bindTransactionFactoryType(JdbcTransactionFactory.class);
                        addMapperClass(AccountMapper.class);
                        addMapperClass(BanMapper.class);
                        addMapperClass(PlayerMapper.class);
                        bind(SqlService.class).to(SqlServiceImpl.class);
                        environmentId("development");
                    }

                },
                new HelperModule() {
					@Override
					public void initialize() {
						add(AccountHelper.class);
						add(BanHelper.class);
					}
                }
        );
        
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

	public static SqlService getSqlService() {
        return inject.getInstance(SqlService.class);
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
