package org.devemu.network.server.message.transfert;

import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterPacket;

@InterPacket(id="3")
public class NewWaitingMessage extends InterMessage {
	
	public int accId;

	@Override
	public void serialize() {}

	@Override
	public void deserialize() {
		accId = in.getInt();
	}
}
