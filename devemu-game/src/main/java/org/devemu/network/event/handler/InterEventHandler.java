package org.devemu.network.event.handler;

import org.devemu.events.Subscribe;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.server.message.inter.ConnectionInterMessage;

public class InterEventHandler {
	
	@Subscribe(InterClientEvent.class)
	public void onConnection(InterClient client,ConnectionInterMessage message) {
		message.serialize();
		client.write(message.getOut());
	}
}
