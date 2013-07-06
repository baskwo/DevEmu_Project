package org.devemu.network.event.event.game;

import org.devemu.network.server.client.GameClient;

public class GameClientEvent {
	private final GameClient client;
    private final Object message;
    
    public GameClientEvent(GameClient client, Object message) {
    	this.client = client;
    	this.message = message;
    }

	public Object getMessage() {
		return message;
	}

	public GameClient getClient() {
		return client;
	}
}
