package org.devemu.network.event.handler;

import java.util.List;

import org.devemu.events.Subscribe;
import org.devemu.network.event.event.game.GameClientEvent;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.server.client.GameClient;
import org.devemu.network.server.message.inter.AccountTicketMessage;
import org.devemu.network.server.message.inter.ConnectionInterMessage;
import org.devemu.network.server.message.transfert.NewWaitingMessage;
import org.devemu.network.server.message.transfert.WaitingAgreedMessage;
import org.devemu.program.Main;
import org.devemu.sql.entity.Account;

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
	
	@Subscribe(GameClientEvent.class)
	public void onTicket(GameClient client, AccountTicketMessage message) {
		if(!waitings.contains(message.aId)) {
			//TODO: Error
			return;
		}
		waitings.remove((Object)message.aId);
		Account acc = Main.getSqlService().findAccountById(""+message.aId);
		if(acc != null) {
			client.setAcc(acc);
			message.answer = true;
		}else
			message.answer = false;
		message.serialize();
		client.write(message.output);
	}
}
