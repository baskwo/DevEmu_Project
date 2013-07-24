package org.devemu.network.inter.client;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.client.ServerClient;

public class InterClient extends ServerClient{
	
	public InterClient(IoSession session) {
		super(session);
	}
}
