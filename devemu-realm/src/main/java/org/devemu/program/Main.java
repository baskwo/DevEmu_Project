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

public class Main {
	private static ConfigReader config = new ConfigReader();

	public static void main(String[] args) {
		long loc1 = System.nanoTime();
		Console.printHeader();
		config.init("config.xml");
		ClientFactory.init();
		DAO.init();
		InterServer.getInstance().start();
		RealmServer.getInstance().start();
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new QueueManager(), 0, 1000, TimeUnit.MILLISECONDS);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new SaveTimer(), 0, 10, TimeUnit.MINUTES);
		System.out.println("Launched time : " + ((double)(System.nanoTime() - loc1) / 1000000000) + " seconds");
	}

	public static Object getConfigValue(ConfigEnum arg1) {
		return config.get(arg1);
	}
}
