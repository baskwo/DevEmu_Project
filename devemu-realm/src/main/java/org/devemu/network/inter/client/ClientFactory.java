package org.devemu.network.inter.client;

import java.util.Map;
import java.util.TreeMap;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.client.BaseClient.State;
import org.devemu.network.server.RealmServer;
import org.devemu.network.server.client.RealmClient;
import org.devemu.network.server.message.server.ServerListMessage;
import org.devemu.program.Main;

public class ClientFactory {
	private static Map<Integer,InterClient> clients = new TreeMap<Integer,InterClient>();
	
	public static void init() {
		int size = Integer.parseInt((String)Main.getConfigValue("devemu.options.game.maxServ"));
		for(int i = 1; i <= size; i++) {
			InterClient client = new InterClient(null);
			client.setGuid(i);
			clients.put(i,client);
		}
	}
	
	public static void refreshServer() {
		ServerListMessage o = new ServerListMessage();
		o.serialize();
		for(IoSession i : RealmServer.getInstance().getManagedSessions().values()) {
			RealmClient client = (RealmClient) i.getAttribute(RealmServer.getInstance().getAcceptor().getHandler());
			if(client.getState() == State.SERVER) {
				i.write(o.output);
			}
		}
	}
	
	public static InterClient get(int arg0) {
		return clients.get(arg0);
	}

	public static Map<Integer,InterClient> getClients() {
		return clients;
	}
}
