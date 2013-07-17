package org.devemu.network.server.message.connect;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(id = "HG")
public class ClientConnectMessage extends Message {

	@Override
	public void serialize() {
		output += "HG";
	}

	@Override
	public void deserialize() {
	}
}
