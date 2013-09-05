package org.devemu.sql.entity.helper;

import java.util.Map;

import org.devemu.sql.entity.Channel;
import org.devemu.sql.entity.Player;

import com.google.common.collect.Maps;

public class ChannelHelper {
	private Map<String,Channel> channels = Maps.newHashMap();
	
	public ChannelHelper() {
		channels.put("i", new Channel("i"));
		channels.put("*", new Channel("*"));
		channels.put("#$p", new Channel("#$p"));
		channels.put("%", new Channel("%"));
		channels.put("!", new Channel("!"));
		channels.put("?", new Channel("?"));
		channels.put(":", new Channel(":"));
	}
	
	public void addPlayer(String chann, Player p) {
		Channel channel = channels.get(chann);
		channel.getPlayers().put(p.getGuid(), p);
	}
	
	public void remPlayer(String chann, int pId) {
		Channel channel = channels.get(chann);
		channel.getPlayers().remove(pId);
	}
	
	public void sendMessageToMap(String chann, String message) {
		Channel channel = channels.get(chann);
		//TODO: Send message to map
	}
	
	public void sendMessageToFight(String chann, String message) {
		Channel channel = channels.get(chann);
		//TODO: Send message to fight
	}
}
