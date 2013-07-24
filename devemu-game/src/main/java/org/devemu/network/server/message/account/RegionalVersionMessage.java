package org.devemu.network.server.message.account;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(id = "AV")
public class RegionalVersionMessage extends Message {
	
	public int regionalId;

	@Override
	public void serialize() {
		output = "AV" + regionalId;
	}

	@Override
	public void deserialize() {
	}
}
