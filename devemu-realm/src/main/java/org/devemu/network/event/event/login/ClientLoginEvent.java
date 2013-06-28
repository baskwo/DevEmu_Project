package org.devemu.network.event.event.login;

import org.devemu.network.server.client.RealmClient;

public class ClientLoginEvent {
	private final RealmClient client;
    private final Object message;
    
    public ClientLoginEvent(RealmClient client, Object message) {
    	this.client = client;
    	this.message = message;
    }

	public RealmClient getClient() {
		return client;
	}

	public Object getMessage() {
		return message;
	}
}
