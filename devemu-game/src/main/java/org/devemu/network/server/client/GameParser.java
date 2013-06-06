package org.devemu.network.server.client;

import org.devemu.network.protocol.Packet;

public class GameParser {
	private GameClient client;

	public GameParser(GameClient arg1) {
		setClient(arg1);
	}
	
	public void parse(Packet arg1) {
		switch(arg1.getIdentificator()) {
		case "AT":
			ClientManager.onTransfert(client, arg1);
			break;
		case "AV":
			ClientManager.onRegionalVersion(client);
			break;
		case "Ag":
			//TODO: "Agfr" Gift
			break;
		case "Af":
			ClientManager.onQueue(client,client.getState());
			break;
		case "AL":
			ClientManager.onWantingList(client);
			break;
		case "Ai":
			ClientManager.onIdentification(client, arg1);
			break;
		case "AS":
			ClientManager.onSelecting(client,arg1);
			break;
		case "AA":
			ClientManager.onCharacterCreation(client, arg1);
			break;
		case "AD":
			ClientManager.onCharacterDeletion(client, arg1);
			break;
		case "GC":
			ClientManager.onGameCreation(client, arg1);
			break;
		}
	}

	public GameClient getClient() {
		return client;
	}

	public void setClient(GameClient client) {
		this.client = client;
	}
}
