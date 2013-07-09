package org.devemu.network.server.message.server;

import org.devemu.network.message.Message;

public class ServerInfoMessage extends Message {
	public String ip;
	public int port;
	public int id;

	@Override
	public void serialize() {
		output += "AYK" + ip + ":" + port + ";" + id;
	}

	@Override
	public void deserialize() {
	}
}
