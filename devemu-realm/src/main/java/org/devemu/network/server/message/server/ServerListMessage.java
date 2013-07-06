package org.devemu.network.server.message.server;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(id="AH",state = State.SERVER)
public class ServerListMessage extends Message {

	@Override
	public void serialize() {
		output = "AH";
		boolean loc0 = true;
		for(InterClient loc8 : ClientFactory.getClients().values()) {
			if(loc0) {
				output += loc8.getGuid() + ";" + loc8.getState().getState() + ";" + loc8.getPopulation().getPopulation() + ";" + (loc8.isAllowNoSubscribe() ? 1 : 0);
				loc0 = false;
			}
			else
				output += "|" + loc8.getGuid() + ";" + loc8.getState().getState() + ";" + loc8.getPopulation().getPopulation() + ";" + (loc8.isAllowNoSubscribe() ? 1 : 0);
		}
	}

	@Override
	public void deserialize() {}

}
