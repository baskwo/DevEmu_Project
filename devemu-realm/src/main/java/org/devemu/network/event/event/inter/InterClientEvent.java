package org.devemu.network.event.event.inter;

import org.devemu.network.inter.client.InterClient;

public class InterClientEvent {
	private final InterClient server;
    private final Object message;
    
    public InterClientEvent(InterClient server, Object message) {
    	this.server = server;
    	this.message = message;
    }

	public Object getMessage() {
		return message;
	}

	public InterClient getServer() {
		return server;
	}
}
