package org.devemu.network.server.message.login.agreed;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
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
				 "AQ" + question.replace(" ", "+") + '\000' +
				 "AH";
		boolean loc0 = true;
		for(InterClient loc8 : ClientFactory.getClients().values()) {
			if(loc0) {
				output += loc8.getGuid() + ";" + loc8.getState().getState() + ";" + loc8.getPopulation().getPopulation() + ";" + (loc8.isAllowNoSubscribe() ? 1 : 0);
				loc0 = false;
			}
			else
				output += "|" + loc8.getGuid() + ";" + loc8.getState().getState() + ";" + loc8.getPopulation().getPopulation() + ";" + (loc8.isAllowNoSubscribe() ? 1 : 0);
		}
		output += '\000';
	}

	@Override
	public void deserialize() {
	}
}
