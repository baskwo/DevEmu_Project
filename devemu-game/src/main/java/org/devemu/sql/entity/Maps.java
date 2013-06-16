package org.devemu.sql.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.devemu.network.server.client.GameClient;

public class Maps implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String date = "";
	private String key = "";
	private List<Player> playersOnMaps = new ArrayList<>();
	private List<Player> marchantsOnMaps = new ArrayList<>();
	
	public List<GameClient> getAllClients() {
		List<GameClient> loc0 = new ArrayList<>();
		for(Player loc1 : playersOnMaps) {
			loc0.add(loc1.getClient());
		}
		return loc0;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<Player> getPlayersOnMaps() {
		return playersOnMaps;
	}
	public void setPlayersOnMaps(List<Player> playersOnMaps) {
		this.playersOnMaps = playersOnMaps;
	}
	public List<Player> getMarchantsOnMaps() {
		return marchantsOnMaps;
	}
	public void setMarchantsOnMaps(List<Player> marchantsOnMaps) {
		this.marchantsOnMaps = marchantsOnMaps;
	}
}
