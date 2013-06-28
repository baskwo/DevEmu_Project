package org.devemu.network.event.handler;

import java.util.Map;

import org.devemu.events.Subscribe;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.server.client.RealmClient;
import org.devemu.network.server.message.server.ServerConnectionMessage;

import com.google.common.collect.HashBiMap;

public class InterEventHandler {
	public static Map<Integer,RealmClient> waitings = HashBiMap.create();
	
	//@Subscribe(InterClientEvent.class)
	public void onConnection(RealmClient client, ServerConnectionMessage message) {
		InterClient loc0 = ClientFactory.get(message.serverId);
		waitings.put(client.getAcc().getId(), client);
	}
}
