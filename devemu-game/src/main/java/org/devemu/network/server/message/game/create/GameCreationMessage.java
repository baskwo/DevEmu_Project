package org.devemu.network.server.message.game.create;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(id = "GC")
public class GameCreationMessage extends Message {
	public int gameType = 0;
	public String characName = "";

	@Override
	public void serialize() {
		output = "GCK|" + gameType + "|" + characName;
	}

	@Override
	public void deserialize() {
		gameType = Integer.parseInt(input.substring(2));
	}
}
