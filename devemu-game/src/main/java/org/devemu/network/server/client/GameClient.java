package org.devemu.network.server.client;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.client.SimpleClient;
import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.Player;

public class GameClient extends SimpleClient {
	
	private Account acc;
	private Player player;
	private int queue = 0;
	private String identification = "";
	private State state = State.TRANSFERT;
	
	public GameClient(IoSession session) {
		super(session);
		getHash().setId(2);
	}
	
	public boolean isCrypt() {
		return getHash().getHash().length() > 0 ? true : false;
	}

	public Account getAcc() {
		return acc;
	}

	public void setAcc(Account acc) {
		this.acc = acc;
	}

	public int getQueue() {
		return queue;
	}

	public void setQueue(int queue) {
		this.queue = queue;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
