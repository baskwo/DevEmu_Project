package org.devemu.network.client;

import org.apache.mina.core.session.IoSession;

public abstract class BaseClient {
	private IoSession session;
	
	public BaseClient(IoSession arg0) {
		session = arg0;
	}
	
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
