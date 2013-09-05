package org.devemu.network.server.message.account;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(id = "AD")
public class CharacterDeletionMessage extends Message{
	public int playerId;
	public String answer;
	
	@Override
	public void serialize() {
		output = "ADE";
	}

	@Override
	public void deserialize() {
		String[] infos = input.substring(2).split("\\|");
		playerId = Integer.parseInt(infos[0]);
		if(infos.length == 2)
			answer = infos[1];
	}
}
