package org.devemu.network.inter.client;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.client.ServerClient;
import org.devemu.utils.enums.ServerPop;
import org.devemu.utils.enums.ServerState;

public class InterClient extends ServerClient{
	private int guid = 0;
	private ServerState state = ServerState.OFFLINE;
	private ServerPop population = ServerPop.FULL;
	private String ip = "";
	private int port = 0;
	private boolean allowNoSubscribe = true;
	
	public InterClient(IoSession session) {
		super(session);
	}
	
	public int getGuid() {
		return guid;
	}
	public void setGuid(int guid) {
		this.guid = guid;
	}
	public ServerState getState() {
		return state;
	}
	public void setState(ServerState state) {
		this.state = state;
	}
	public ServerPop getPopulation() {
		return population;
	}
	public void setPopulation(ServerPop population) {
		this.population = population;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public boolean isAllowNoSubscribe() {
		return allowNoSubscribe;
	}

	public void setAllowNoSubscribe(boolean allowNoSubscribe) {
		this.allowNoSubscribe = allowNoSubscribe;
	}
}
