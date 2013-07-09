package org.devemu.network.server.message.transfert;

import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterPacket;

@InterPacket(id = "3")
public class ServerWaitingMessage extends InterMessage {
	
	public int accId;

	@Override
	public void serialize() {
		out.put((byte)3);
		out.putInt(accId);
		out.flip();
	}

	@Override
	public void deserialize() {}
}
