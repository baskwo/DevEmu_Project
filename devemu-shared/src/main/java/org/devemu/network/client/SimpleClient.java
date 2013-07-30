package org.devemu.network.client;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.CryptKey;

public class SimpleClient extends BaseClient {
	private CryptKey hash = new CryptKey();
	
	public SimpleClient(IoSession session) {
		super(session);
	}
	
	public SimpleClient() {}

	public CryptKey getHash() {
		return hash;
	}

	public void setHash(CryptKey hash) {
		this.hash = hash;
	}
	
	
}
