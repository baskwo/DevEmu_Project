package org.devemu.network.server.message.login;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(state = State.VERSION)
public class LoginVersionMessage extends Message {
	public String version = "";

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public void serialize() {
	}

	@Override
	public void deserialize() {
		version = input;
	}
}
