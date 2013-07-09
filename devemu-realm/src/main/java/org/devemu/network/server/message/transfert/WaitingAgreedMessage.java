package org.devemu.network.server.message.transfert;

import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterPacket;

@InterPacket(id = "4")
public class WaitingAgreedMessage extends InterMessage {
	public int aId;

	@Override
	public void serialize() {
	}

	@Override
	public void deserialize() {
		aId = in.getInt();
	}
}
