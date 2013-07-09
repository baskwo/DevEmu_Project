package org.devemu.network.event.handler;

import java.util.List;

import org.devemu.events.Subscribe;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.server.message.inter.ConnectionInterMessage;
import org.devemu.network.server.message.transfert.NewWaitingMessage;
import org.devemu.network.server.message.transfert.WaitingAgreedMessage;

import com.google.common.collect.Lists;

public class InterEventHandler {
	public static List<Integer> waitings = Lists.newArrayList();
	
	@Subscribe(InterClientEvent.class)
	public void onConnection(InterClient client,ConnectionInterMessage message) {
		message.serialize();
		client.write(message.getOut());
	}
	
	@Subscribe(InterClientEvent.class)
	public void onConnection(InterClient client,NewWaitingMessage message) {
		int aId = message.accId;
		waitings.add(aId);
		
		WaitingAgreedMessage o = new WaitingAgreedMessage();
		o.accId = aId;
		o.serialize();
		client.write(o.out);
	}
}
