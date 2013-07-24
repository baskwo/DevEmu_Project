package org.devemu.network.event.event.game;

import org.devemu.network.server.client.GameClient;

public class GameClientReuseEvent extends GameEvent{
    public GameClientReuseEvent(GameClient client, Object message) {
    	super(client,message);
    }
}
