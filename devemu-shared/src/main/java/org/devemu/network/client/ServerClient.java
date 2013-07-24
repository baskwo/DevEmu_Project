package org.devemu.network.client;

import org.apache.mina.core.session.IoSession;

public class ServerClient extends BaseClient {

	public ServerClient(IoSession session) {
		super(session);
	}
}
