package org.devemu.network.server.message.login;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(state = State.ACCOUNT)
public class LoginAccountMessage extends Message {
	public String username;
	public String passHash;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassHash() {
		return passHash;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	@Override
	public void serialize() {
	}

	@Override
	public void deserialize() {
		String[] infos = input.split("\n");
		if(infos.length == 2) {
			username = infos[0];
			passHash = infos[1];
		}
	}
}
