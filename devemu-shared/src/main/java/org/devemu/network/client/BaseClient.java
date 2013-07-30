package org.devemu.network.client;

import org.apache.mina.core.session.IoSession;

public abstract class BaseClient {
	public enum State {
		CONNECT,
		VERSION,
		ACCOUNT,
		SERVER,
		TRANSFERT,
		SELECTING,
		NULL
	}
	
	private IoSession session;
	
	public BaseClient(IoSession session) {
		this.session = session;
	}
	
	public BaseClient() {}
	
	public void write(Object arg1) {
		getSession().write(arg1);
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}
}
