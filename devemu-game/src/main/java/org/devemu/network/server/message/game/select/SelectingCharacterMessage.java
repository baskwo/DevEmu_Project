package org.devemu.network.server.message.game.select;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.sql.entity.Player;
import org.devemu.sql.manager.PlayerManager;

@Packet(id = "AS")
public class SelectingCharacterMessage extends Message {
	//Deserialize
	public int playerId;
	//Serialize
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
