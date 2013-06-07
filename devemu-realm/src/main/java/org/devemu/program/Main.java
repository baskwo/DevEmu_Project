package org.devemu.program;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.devemu.network.inter.InterServer;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.server.RealmServer;
import org.devemu.sql.dao.DAO;
import org.devemu.utils.config.ConfigEnum;
import org.devemu.utils.config.ConfigReader;
import org.devemu.utils.main.Console;
import org.devemu.utils.queue.QueueManager;
import org.devemu.utils.timer.SaveTimer;

import com.google.common.base.Stopwatch;

public class Main {
	private static ConfigReader config = new ConfigReader();

	public static void main(String[] args) {
		Stopwatch loc1 = new Stopwatch().start();
		Console.printHeader();
		config.init("config.conf");
		ClientFactory.init();
		DAO.init();
		InterServer.getInstance().start();
		RealmServer.getInstance().start();
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new QueueManager(), 0, 1000, TimeUnit.MILLISECONDS);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new SaveTimer(), 0, 10, TimeUnit.MINUTES);
		loc1.stop();
		System.out.println("Launched time : " + ((double)(loc1.elapsedTime(TimeUnit.NANOSECONDS))/1000000000) + " seconds");
	}

	public static Object getConfigValue(ConfigEnum arg1) {
		return config.get(arg1);
	}
}
