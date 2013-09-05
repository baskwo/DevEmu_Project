package org.devemu.network.server.message.account;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.sql.entity.Player;
import org.devemu.sql.entity.helper.PlayerHelper;

import com.google.inject.Inject;

@Packet(id = "As")
public class CharacterStatsMessage extends Message {
	public Player player;
	@Inject PlayerHelper playerHelper;

	@Override
	public void serialize() {
		output = "As" + playerHelper.getXpToString(player);
		for(String data : playerHelper.getAsData(player)) {
			output += "|" + data;
		}
	}

	@Override
	public void deserialize() {
	}
}
