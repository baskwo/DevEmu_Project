package org.devemu.network.inter.client;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.client.ServerClient;
import org.devemu.network.protocol.ServerPacket;

public class InterClient extends ServerClient{
	private InterParser parser = new InterParser(this);
	
	public InterClient(IoSession arg0) {
		super(arg0);
	}
	
	public void parse(ServerPacket arg0) {
		parser.parse(arg0);
	}

	public InterParser getParser() {
		return parser;
	}

	public void setParser(InterParser parser) {
		this.parser = parser;
	}
}
