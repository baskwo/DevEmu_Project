package org.devemu.program;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.devemu.network.inter.InterServer;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.server.RealmServer;
import org.devemu.utils.config.ConfigEnum;
import org.devemu.utils.config.ConfigReader;
import org.devemu.utils.queue.QueueManager;

public class Main {
	private static ConfigReader config = new ConfigReader();

	public static void main(String[] args) {
		long loc1 = System.nanoTime();
		config.init("config.xml");
		ClientFactory.init();
		//TODO: SQL Init
		InterServer.getInstance().start();
		RealmServer.getInstance().start();
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new QueueManager(), 0, 1000, TimeUnit.MILLISECONDS);
		System.out.println("Launched time : " + ((double)(System.nanoTime() - loc1) / 1000000000) + " seconds");
	}

	public static Object getConfigValue(ConfigEnum arg1) {
		return config.get(arg1);
	}
}
