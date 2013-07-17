package org.devemu.network.server.message.inter;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(id = "AT")
public class AccountTicketMessage extends Message {
	public int aId;
	
	public boolean answer;

	@Override
	public void serialize() {
		output = "AT" + (answer ? "K0" : "E");
	}

	@Override
	public void deserialize() {
		aId = Integer.parseInt(input.substring(2));
	}
}
