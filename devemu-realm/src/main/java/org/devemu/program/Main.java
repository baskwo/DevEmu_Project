package org.devemu.program;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.devemu.inject.ConfigModule;
import org.devemu.inject.JarModuleInstaller;
import org.devemu.inject.ModuleInstaller;
import org.devemu.inject.ServiceManager;
import org.devemu.sql.SqlService;
import org.devemu.sql.SqlServiceImpl;
import org.devemu.sql.entity.mapper.AccountMapper;
import org.devemu.sql.entity.mapper.BanMapper;
import org.devemu.sql.entity.mapper.PlayerMapper;
import org.devemu.utils.Stopwatch;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.dbcp.BasicDataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static Injector inject;
    private static Config config;

	public static void main(String[] args) {
        final ClassLoader loader = ClassLoader.getSystemClassLoader();
        config = ConfigFactory.parseFileAnySyntax(new File("./devemu-realm"));

        inject = Guice.createInjector(
                ConfigModule.of(config),
                JarModuleInstaller.from(config.getString("devemu.mods.dir")),
                ModuleInstaller.of(loader, config.getConfig("devemu.mods")),
                new MyBatisModule() {

                    @Override
                    protected void initialize() {
                        bindDataSourceProviderType(BasicDataSourceProvider.class);
                        bindTransactionFactoryType(JdbcTransactionFactory.class);
                        addMapperClass(AccountMapper.class);
                        addMapperClass(BanMapper.class);
                        addMapperClass(PlayerMapper.class);
                        bind(SqlService.class).to(SqlServiceImpl.class);
                        bind(String.class).annotatedWith(Names.named("JDBC.url")).toInstance(config.getString("devemu.service.db.url"));
                        bind(String.class).annotatedWith(Names.named("JDBC.driver")).toInstance(config.getString("devemu.service.db.driver"));
                        bind(String.class).annotatedWith(Names.named("JDBC.username")).toInstance(config.getString("devemu.service.db.user"));
                        bind(String.class).annotatedWith(Names.named("JDBC.password")).toInstance(config.getString("devemu.service.db.pass"));
                        environmentId("development");
                    }

                }
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

	public static SqlService getSqlService() {
        return inject.getInstance(SqlService.class);
	}

	public static String getConfigValue(String field) {
		return config.getString(field);
	}
	
	public static void log(String message,Class<?> logging) {
		LoggerFactory.getLogger(logging).debug(message);
	}
}
