<<<<<<< HEAD:DevemuGame/src/org/devemu/sql/entity/Channel.java
package org.devemu.sql.entity;

import java.util.HashMap;
import java.util.Map;

public class Channel {
	private String id = "";
	private Map<Integer,Player> players = new HashMap<>();
	
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
=======
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
>>>>>>> 800500b6eda7a3b433414a80b9c5ef278d93b04e:devemu-game/src/main/java/org/devemu/sql/entity/Channel.java
