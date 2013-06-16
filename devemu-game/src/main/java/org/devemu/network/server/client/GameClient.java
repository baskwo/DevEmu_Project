package org.devemu.network.server.client;

import static com.google.common.base.Throwables.propagate;

import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.client.SimpleClient;
import org.devemu.network.protocol.Packet;
import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.Player;

public class GameClient extends SimpleClient {
	
	private GameParser parser;
	private Account acc;
	private Player player;
	private int queue = 0;
	private int queueCur = 0;
	private String identification = "";
	
	public GameClient(IoSession arg1) {
		super(arg1);
		parser = new GameParser(this);
		getHash().setId(2);
	}
	
	public void parse(Packet arg1) {
		try {
			parser.parse(arg1);
		}catch(Exception e){throw propagate(e);}
	}
	
	public boolean isCrypt() {
		return getHash().getHash().length() > 0 ? true : false;
	}
	
	public void send(List<Packet> arg1) {
		for(Packet loc1 : arg1) {
			write(loc1.toString());
		}
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
}
