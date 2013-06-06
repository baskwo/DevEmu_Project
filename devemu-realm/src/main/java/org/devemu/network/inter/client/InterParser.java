package org.devemu.network.inter.client;

import org.devemu.network.InterId;
import org.devemu.network.protocol.ServerPacket;

public class InterParser {
	private InterClient client = null;
	
	public InterParser(InterClient arg0) {
		client = arg0;
	}
	
	@SuppressWarnings("incomplete-switch")
	public void parse(ServerPacket arg0) {
		InterId loc1 = InterId.getId((byte) arg0.getId());
		switch(loc1) {
		case ACC_WAITING_AGREED:
			InterManager.onWaitingAgreed(client, arg0);
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
