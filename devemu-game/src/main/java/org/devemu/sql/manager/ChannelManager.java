package org.devemu.sql.manager;

import java.util.Map;

import org.devemu.sql.entity.Channel;
import org.devemu.sql.entity.Player;

import com.google.common.collect.Maps;

public class ChannelManager {
	private static Map<String,Channel> channels = Maps.newHashMap();
	
	static {
		channels.put("i", new Channel("i"));
		channels.put("*", new Channel("*"));
		channels.put("#$p", new Channel("#$p"));
		channels.put("%", new Channel("%"));
		channels.put("!", new Channel("!"));
		channels.put("?", new Channel("?"));
		channels.put(":", new Channel(":"));
	}
	
	public static void addPlayer(String chann, Player p) {
		Channel channel = channels.get(chann);
		channel.getPlayers().put(p.getGuid(), p);
	}
	
	public static void remPlayer(String chann, int pId) {
		Channel channel = channels.get(chann);
		channel.getPlayers().remove(pId);
	}
	
	public static void sendMessageToMap(String chann, String message) {
		Channel channel = channels.get(chann);
		//TODO: Send message to map
	}
	
	public static void sendMessageToFight(String chann, String message) {
		Channel channel = channels.get(chann);
		//TODO: Send message to fight
	}
}
