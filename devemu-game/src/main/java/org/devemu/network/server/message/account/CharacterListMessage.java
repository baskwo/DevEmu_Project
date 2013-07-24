package org.devemu.network.server.message.account;

import java.util.List;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.sql.entity.Player;
import org.devemu.sql.manager.PlayerManager;

@Packet(id = "AL")
public class CharacterListMessage extends Message {
	public long aboTime;
	public List<Player> players;

	@Override
	public void serialize() {
		output = "ALK" + aboTime + "|" + players.size();
		for(Player p : players) {
			output += "|" + PlayerManager.toALK(p);
		}
	}

	@Override
	public void deserialize() {
	}
}
