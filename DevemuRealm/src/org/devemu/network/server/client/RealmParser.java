package org.devemu.network.server.client;

import org.devemu.network.protocol.Packet;

public class RealmParser {
	private RealmClient client;

	public RealmParser(RealmClient arg1) {
		setClient(arg1);
	}
	
	@SuppressWarnings("incomplete-switch")
	public void parse(Packet arg1) {
		switch(client.getState()) {
		case SERVER:
			switch(arg1.getIdentificator()) {
			case "Af":
				ClientManager.onQueue(client);
				break;
			case "Ax":
				ClientManager.onServersList(client);
				break;
			case "AX":
				ClientManager.onConnection(arg1, client);
				break;
			}
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	public void parse(String arg1) {
		switch(client.getState()) {
		case VERSION:
			ClientManager.onVersion(arg1, client);
			break;
		case ACCOUNT:
			if(arg1.contains("\n"))
				ClientManager.onAccount(arg1, client);
			else if(arg1.startsWith("Af"))
				return;
			else{
				Packet loc1 = new Packet();
				loc1.setIdentificator("Al");
				loc1.setFirstParam("EE");
				client.write(loc1.toString());
			}
			break;
		}
	}

	public RealmClient getClient() {
		return client;
	}

	public void setClient(RealmClient client) {
		this.client = client;
	}
}
