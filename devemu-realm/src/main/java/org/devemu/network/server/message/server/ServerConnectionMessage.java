package org.devemu.network.server.message.server;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(id="AX",state = State.SERVER)
public class ServerConnectionMessage extends Message {
	public int serverId;

	@Override
	public void serialize() {
	}

	@Override
	public void deserialize() {
		serverId = Integer.parseInt(input.substring(2));
	}
}
