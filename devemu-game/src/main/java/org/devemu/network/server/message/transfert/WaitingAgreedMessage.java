package org.devemu.network.server.message.transfert;

import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterPacket;
import org.devemu.program.Main;

@InterPacket(id = "4")
public class WaitingAgreedMessage extends InterMessage {
	
	public int accId;

	@Override
	public void serialize() {
		out.put((byte)4);
		out.put((byte) Main.getGuid());
		out.putInt(accId);
		out.flip();
	}

	@Override
	public void deserialize() {}
}
