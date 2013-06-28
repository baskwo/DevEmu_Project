package org.devemu.network.event.event.inter;

import org.devemu.network.server.client.RealmClient;

public class InterClientEvent {
	private final RealmClient client;
    private final Object message;
    
    public InterClientEvent(RealmClient client, Object message) {
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
