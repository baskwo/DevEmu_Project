package org.devemu.network.server.message.connect;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Packet;
import org.devemu.network.message.Message;

@Packet(state = State.CONNECT)
public class LoginConnectMessage extends Message {
	
	public String salt = "";

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public void serialize() {
		output = "HC" + salt;
	}

	@Override
	public void deserialize() {
		
	}
}
