package org.devemu.network.event.handler;

import java.util.Map;

import org.devemu.events.Subscribe;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.event.event.login.ClientLoginEvent;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.server.client.RealmClient;
import org.devemu.network.server.message.connect.ServerConnectMessage;
import org.devemu.network.server.message.server.ServerConnectionMessage;

import com.google.common.collect.HashBiMap;

public class InterEventHandler {
	public static Map<Integer,RealmClient> waitings = HashBiMap.create();
	
	@Subscribe(ClientLoginEvent.class)
	public void onConnection(RealmClient client, ServerConnectionMessage message) {
		InterClient loc0 = ClientFactory.get(message.serverId);
		waitings.put(client.getAcc().getId(), client);
		//TODO:Waiting packet
	}
	
	@Subscribe(InterClientEvent.class)
	public void onServerConnect(InterClient server, ServerConnectMessage message) {
		server.setState(message.state);
		server.setPopulation(message.population);
		server.setAllowNoSubscribe(message.allowNoSubscribe);
		server.setIp(message.ip);
		server.setPort(message.port);
		ClientFactory.refreshServer();
	}
}
