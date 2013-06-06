package org.devemu.network.server.client;

import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.client.SimpleClient;
import org.devemu.network.protocol.Packet;
import org.devemu.sql.entity.Account;
import org.devemu.utils.Crypt;

public class RealmClient extends SimpleClient{
	public enum State {
		VERSION,
		ACCOUNT,
		SERVER
	}
	
	private String salt = "";
	private RealmParser parser;
	private State state = State.VERSION;
	private Account acc = null;
	private int queue = 0;
	private int queueCur = 0;
	
	public RealmClient(IoSession arg1) {
		super(arg1);
		parser = new RealmParser(this);
		salt = Crypt.randomString(32);
		getHash().setId(1);
	}
	
	public void parse(Packet arg1) {
		try {
			parser.parse(arg1);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void parse(String arg1) {
		try {
			parser.parse(arg1);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public boolean isCrypt() {
		return getHash().getHash().length() > 0 ? true : false;
	}
	
	public void write(List<Packet> arg1) {
		for(Packet loc1 : arg1) {
			write(loc1.toString());
		}
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public RealmParser getParser() {
		return parser;
	}

	public void setParser(RealmParser parser) {
		this.parser = parser;
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
