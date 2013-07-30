package org.devemu.network.server.message.game.select;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.queue.QueueListener;
import org.devemu.sql.entity.Player;
import org.devemu.sql.manager.PlayerManager;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@Packet(id = "AS")
public class SelectingCharacterMessage extends Message {
	//Other
	@Inject @Named("selection") public QueueListener listener;
	//Deserialize
	public int playerId;
	//Serialize (Oh yeah, triforce!)
	public Player player;

	@Override
	public void serialize() {
		output = "ASK|" + PlayerManager.toASK(player);
	}

	@Override
	public void deserialize() {
		playerId = Integer.parseInt(input.substring(2));
	}
}
