package org.devemu.program;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.devemu.network.inter.InterServer;
import org.devemu.network.server.GameServer;
import org.devemu.sql.dao.DAO;
import org.devemu.utils.config.ConfigEnum;
import org.devemu.utils.config.ConfigReader;
import org.devemu.utils.enums.ServerPop;
import org.devemu.utils.enums.ServerState;
import org.devemu.utils.main.Console;
import org.devemu.utils.queue.QueueManager;
import org.devemu.utils.timer.SaveTimer;

public class Main {
	public enum Queue {
		TRANSFERT,
		SELECTING
	}
	
	private static ConfigReader config = new ConfigReader();
	private static ServerState state = ServerState.OFFLINE;
	private static ServerPop population = ServerPop.FULL;
	private static int guid = 0;
	private static boolean allowNoSubscribe = true;
	private static Map<Queue,QueueManager> queues = new TreeMap<>();

	public static void main(String[] args) {
		long loc1 = System.nanoTime();
		Console.printHeader();
		config.init("config.xml");
		DAO.init();
		state = ServerState.ONLINE;
		population = ServerPop.RECOMMENDED;
		guid = Integer.parseInt((String)getConfigValue(ConfigEnum.GAME_GUID));
		allowNoSubscribe = Boolean.parseBoolean((String)getConfigValue(ConfigEnum.ALLOW_NO_SUSCRIBE));
		QueueManager loc3 = new QueueManager(Queue.TRANSFERT);
		QueueManager loc4 = new QueueManager(Queue.SELECTING);
		queues.put(Queue.TRANSFERT, loc3);
		queues.put(Queue.SELECTING, loc4);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(loc3, 0, 1000, TimeUnit.MILLISECONDS);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(loc4, 0, 1000, TimeUnit.MILLISECONDS);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new SaveTimer(), 0, 10, TimeUnit.MINUTES);
		InterServer.getInstance().start();
		GameServer.getInstance().start();
		System.out.println("Launched time : " + ((double)(System.nanoTime() - loc1) / 1000000000) + " seconds");
	}
	
	public static QueueManager getQueue(Queue arg0) {
		return queues.get(arg0);
	}

	public static Object getConfigValue(ConfigEnum arg1) {
		return config.get(arg1);
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
