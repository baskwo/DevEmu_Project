package org.devemu.network.server.message.connect;

import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterPacket;

@InterPacket(id = "2")
public class ServerConnectAgreeMessage extends InterMessage {

	@Override
	public void serialize() {
		out.put((byte)2);
		out.flip();
	}

	@Override
	public void deserialize() {}
}
