package org.devemu.network.inter.client;

import org.devemu.network.InterId;
import org.devemu.network.protocol.ServerPacket;

public class InterParser {
	private InterClient client = null;
	
	public InterParser(InterClient arg0) {
		setClient(arg0);
	}
	
	@SuppressWarnings("incomplete-switch")
	public void parse(ServerPacket arg0) {
		InterId loc1 = InterId.getId((byte) arg0.getId());
		switch(loc1) {
		case CONNECT:
			InterManager.onConnect(client);
			break;
		case ACC_WAITING:
			InterManager.onWaiting(client, arg0);
			break;
		}
	}

	public InterClient getClient() {
		return client;
	}

	public void setClient(InterClient client) {
		this.client = client;
	}
}
