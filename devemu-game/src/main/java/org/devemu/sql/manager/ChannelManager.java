package org.devemu.sql.manager;

import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.entity.Channel;
import org.devemu.sql.entity.Player;

public class ChannelManager {
	private static Map<String,Channel> channels = new HashMap<String,Channel>();
	
	static {
		channels.put("i", new Channel("i"));
		channels.put("*", new Channel("*"));
		channels.put("#$p", new Channel("#$p"));
		channels.put("%", new Channel("%"));
		channels.put("!", new Channel("!"));
		channels.put("?", new Channel("?"));
		channels.put(":", new Channel(":"));
	}
	
	public static void addPlayer(String arg0, Player arg1) {
		Channel loc0 = channels.get(arg0);
		loc0.getPlayers().put(arg1.getGuid(), arg1);
	}
	
	public static void remPlayer(String arg0, int arg1) {
		Channel loc0 = channels.get(arg0);
		loc0.getPlayers().remove(arg1);
	}
	
	public static void sendMessageToMap(String arg0, String arg1) {
		Channel loc0 = channels.get(arg0);
		//TODO: Send message to map
	}
	
	public static void sendMessageToFight(String arg0, String arg1) {
		Channel loc0 = channels.get(arg0);
		//TODO: Send message to fight
	}
}
