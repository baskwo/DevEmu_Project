package org.devemu.network.event.handler;

import org.devemu.events.Subscribe;
import org.devemu.network.client.BaseClient.State;
import org.devemu.network.event.event.game.GameClientEvent;
import org.devemu.network.event.event.game.GameClientReuseEvent;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.network.server.client.GameClient;
import org.devemu.network.server.message.account.CharacterCreationMessage;
import org.devemu.network.server.message.account.CharacterDeletionMessage;
import org.devemu.network.server.message.account.CharacterListMessage;
import org.devemu.network.server.message.account.CharacterStatsMessage;
import org.devemu.network.server.message.account.RegionalVersionMessage;
import org.devemu.network.server.message.game.create.GameCreationMessage;
import org.devemu.network.server.message.game.select.SelectingCharacterMessage;
import org.devemu.network.server.message.queue.QueueMessage;
import org.devemu.queue.QueueListener;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import static com.google.common.base.Throwables.propagate;

public class GameEventHandler {
	@Inject @Named("selection") QueueListener sListener;
	@Inject @Named("transfert") QueueListener tListener;
	@Inject MessageFactory factory;
	
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
		//Send JS (All job stats)
		//Send JX (Job level,xp)
		//Send JO (Job option)
		//Send OT (Item tool)
		//Send Align 
		//Send AddCanal
		//Send gS if guildMember
		//Send ZoneAlign
		//Send SpellList
		//Send EmoteList
		//Send Restriction
		//Send Ow (Pod use,max)
		//Send SeeFriendConnection
		//Send online to friend
		//Send welcome message
		//Send ILS
	}
	
	@Subscribe(GameClientEvent.class)
	public void onGameCreation(GameClient client, GameCreationMessage message) {
		message.characName = client.getPlayer().getName();
		message.serialize();
		client.write(message.output);
		
		CharacterStatsMessage o = (CharacterStatsMessage) factory.getMessage("As");
		o.player = client.getPlayer();
		o.serialize();
		client.write(o.output);
	}
	
	@Subscribe(GameClientEvent.class)
	public void onPlayerDeletion(GameClient client, CharacterDeletionMessage message) {
		if(!client.getAccHelper().removePlayer(message.playerId, client.getAcc())) {
			message.serialize();
			client.write(message.output);
			return;
		}
		try {
			onCharacterList(client,(CharacterListMessage) factory.getMessage("AL",State.NULL));
		} catch (MessageNotFoundException e) {
			throw propagate(e);
		}
	}
	
	@Subscribe(GameClientEvent.class)
	public void onPlayerCreation(GameClient client, CharacterCreationMessage message) {
		
	}
}
