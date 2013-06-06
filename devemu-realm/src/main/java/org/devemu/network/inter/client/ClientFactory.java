package org.devemu.network.inter.client;

import java.util.Map;
import java.util.TreeMap;

import org.devemu.program.Main;
import org.devemu.utils.config.ConfigEnum;

public class ClientFactory {
	private static Map<Integer,InterClient> clients = new TreeMap<Integer,InterClient>();
	
	public static void init() {
		int loc1 = Integer.parseInt((String)Main.getConfigValue(ConfigEnum.MAX_SERVER));
		for(int i = 1; i <= loc1; i++) {
			InterClient loc0 = new InterClient(null);
			loc0.setGuid(i);
			clients.put(i,loc0);
		}
	}
	
	public static InterClient get(int arg0) {
		return clients.get(arg0);
	}

	public static Map<Integer,InterClient> getClients() {
		return clients;
	}
}
