package org.devemu.sql.entity;

import java.util.ArrayList;
import java.util.List;

import org.devemu.network.server.client.GameClient;
import org.devemu.sql.dao.DAO;

public class Maps {
	private int id = 0;
	private String date = "";
	private String key = "";
	private List<Integer> playersOnMaps = new ArrayList<>();
	private List<Integer> marchantsOnMaps = new ArrayList<>();
	
	public List<GameClient> getAllClients() {
		List<GameClient> loc0 = new ArrayList<>();
		for(int loc1 : playersOnMaps) {
			loc0.add(DAO.getPlayerDAO().find(loc1).getClient());
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
	public List<Integer> getPlayersOnMaps() {
		return playersOnMaps;
	}
	public void setPlayersOnMaps(List<Integer> playersOnMaps) {
		this.playersOnMaps = playersOnMaps;
	}
	public List<Integer> getMarchantsOnMaps() {
		return marchantsOnMaps;
	}
	public void setMarchantsOnMaps(List<Integer> marchantsOnMaps) {
		this.marchantsOnMaps = marchantsOnMaps;
	}
}
