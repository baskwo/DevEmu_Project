package org.devemu.network.server.client;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.client.SimpleClient;
import org.devemu.sql.entity.Account;
import org.devemu.utils.Crypt;

public class RealmClient extends SimpleClient{
	
	private String salt = "";
	private State state = State.CONNECT;
	private Account acc = null;
	private int queue = 0;
	private int queueCur = 0;
	
	public RealmClient(IoSession arg1) {
		super(arg1);
		salt = Crypt.randomString(32);
		getHash().setId(1);
	}
	
	public boolean isCrypt() {
		return getHash().getHash().length() > 0 ? true : false;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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

	public int getQueueCur() {
		return queueCur;
	}

	public void setQueueCur(int queueCur) {
		this.queueCur = queueCur;
	}
}
