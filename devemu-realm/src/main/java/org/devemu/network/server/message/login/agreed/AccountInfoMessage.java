package org.devemu.network.server.message.login.agreed;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.network.server.message.server.ServerListMessage;
import org.devemu.program.Main;

@Packet(id="Al", state = State.SERVER)
public class AccountInfoMessage extends Message {
	
	public boolean isAdmin;
	public String pseudo;
	public String question;

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public void serialize() {
		output = "Alk" + (isAdmin ? "1" : "0") + '\000' +
				 "Ad" + pseudo + '\000' +
				 "Ac" + Main.getConfigValue("devemu.options.realm.community") + '\000' +
				 "AQ" + question.replace(" ", "+") + '\000';
		ServerListMessage o = new ServerListMessage();
		o.serialize();
		output += o.output;
	}

	@Override
	public void deserialize() {
	}
}
