package org.devemu.network.event.handler;

import org.devemu.events.Subscribe;
import org.devemu.network.client.BaseClient.State;
import org.devemu.network.event.event.game.GameClientEvent;
import org.devemu.network.event.event.game.GameClientReuseEvent;
import org.devemu.network.server.client.GameClient;
import org.devemu.network.server.message.account.CharacterListMessage;
import org.devemu.network.server.message.account.RegionalVersionMessage;
import org.devemu.network.server.message.game.select.SelectingCharacterMessage;
import org.devemu.network.server.message.queue.QueueMessage;
import org.devemu.queue.QueueListener;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class GameEventHandler {
	@Inject @Named("selection") QueueListener sListener;
	@Inject @Named("transfert") QueueListener tListener;
	
	@Subscribe(GameClientEvent.class)
	public void onRegional(GameClient client, RegionalVersionMessage message) {
		message.regionalId = 0;
		message.serialize();
		client.write(message.output);
	}
	
	@Subscribe(GameClientEvent.class)
	public void onCharacterWaitingList(GameClient client, CharacterListMessage message) {
		tListener.add(client);
	}
	
	@Subscribe(GameClientReuseEvent.class)
	public void onCharacterList(GameClient client, CharacterListMessage message) {
		message.aboTime = client.getAccHelper().getAboTime(client.getAcc());
		message.players = client.getAcc().getPlayers();
		message.serialize();
		client.write(message.output);
		client.setState(State.SELECTING);
	}
	
	@Subscribe(GameClientEvent.class)
	public void onQueue(GameClient client, QueueMessage message) {
		message.currentPos = client.getQueue();
		message.subscriber = client.getAccHelper().getAboTime(client.getAcc()) > 0 ? true : false;
		if(client.getState() == State.TRANSFERT) {
			message.aboSize = tListener.getQueueAbo().size();
			message.nonAboSize = tListener.getQueue().size();
			message.queueId = 1;
		}else{
			message.aboSize = sListener.getQueueAbo().size();
			message.nonAboSize = sListener.getQueue().size();
			message.queueId = 2;
		}
		message.serialize();
		client.write(message.output);
	}
	
	@Subscribe(GameClientEvent.class)
	public void onSelectingWaiting(GameClient client, SelectingCharacterMessage message) {
		client.setPlayer(client.getAccHelper().getPlayer(message.playerId, client.getAcc()));
		sListener.add(client);
	}
	
	@Subscribe(GameClientReuseEvent.class)
	public void onSelecting(GameClient client, SelectingCharacterMessage message) {
		message.player = client.getPlayer();
		message.serialize();
		client.write(message.output);
		//TODO: Send OS (Object Set bonus)
		//TODO: Send JS (All job stats)
		//TODO: Send JX (Job level,xp)
		//TODO: Send JO (Job option)
		//TODO: Send OT (Item tool)
		//TODO: Send Align 
		//TODO: Send AddCanal
		//TODO: Send gS if guildMember
		//TODO: Send ZoneAlign
		//TODO: Send SpellList
		//TODO: Send EmoteList
		//TODO: Send Restriction
		//TODO: Send Ow (Pod use,max)
		//TODO: Send SeeFriendConnection
		//TODO: Send online to friend
		//TODO: Send welcome message
		//TODO: Send ILS
	}
}
