package org.devemu.sql.entity;

import java.util.HashMap;
import java.util.Map;

public class Channel {
	private String id = "";
	private Map<Integer,Player> players = new HashMap<Integer,Player>();
	
	public Channel(String arg0) {
		id = arg0;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<Integer,Player> getPlayers() {
		return players;
	}
	public void setPlayers(Map<Integer,Player> players) {
		this.players = players;
	}
}
