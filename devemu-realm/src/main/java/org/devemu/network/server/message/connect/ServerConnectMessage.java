package org.devemu.network.server.message.connect;

import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterPacket;
import org.devemu.utils.enums.ServerPop;
import org.devemu.utils.enums.ServerState;

@InterPacket(id = "1")
public class ServerConnectMessage extends InterMessage {
	public boolean allowNoSubscribe;
	public ServerState state;
	public ServerPop population;
	public String ip;
	public int port;

	@Override
	public void serialize() {
	}

	@Override
	public void deserialize() {
		state = ServerState.get(in.get());
		population = ServerPop.get(in.get());
		ip = readString();
		port = in.getInt();
		allowNoSubscribe = in.get() == 1 ? true : false;
	}
}
