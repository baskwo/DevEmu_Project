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
import org.devemu.sql.manager.AccountManager;
import org.devemu.utils.queue.QueueSelector;

public class GameEventHandler {
	@Subscribe(GameClientEvent.class)
	public void onRegional(GameClient client, RegionalVersionMessage message) {
		message.regionalId = 0;
		message.serialize();
		client.write(message.output);
	}
	
	@Subscribe(GameClientEvent.class)
	public void onCharacterWaitingList(GameClient client, CharacterListMessage message) {
		QueueSelector.getSelector(State.TRANSFERT).addToQueue(client);
	}
	
	@Subscribe(GameClientReuseEvent.class)
	public void onCharacterList(GameClient client, CharacterListMessage message) {
		message.aboTime = AccountManager.getAboTime(client.getAcc());
		message.players = client.getAcc().getPlayers();
		message.serialize();
		client.write(message.output);
		client.setState(State.SELECTING);
	}
	
	@Subscribe(GameClientEvent.class)
	public void onQueue(GameClient client, QueueMessage message) {
		message.currentPos = client.getQueue();
		message.subscriber = AccountManager.getAboTime(client.getAcc()) > 0 ? true : false;
		message.state = client.getState();
		message.serialize();
		client.write(message.output);
	}
	
	@Subscribe(GameClientEvent.class)
	public void onSelectingWaiting(GameClient client, SelectingCharacterMessage message) {
		client.setPlayer(AccountManager.getPlayer(message.playerId, client.getAcc()));
		QueueSelector.getSelector(State.SELECTING).addToQueue(client);
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
