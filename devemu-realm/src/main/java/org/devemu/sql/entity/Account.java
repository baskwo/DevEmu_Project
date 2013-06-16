package org.devemu.sql.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	private String name = "";
	private String password = "";
	private int level = 0;
	private String pseudo = "";
	private String question = "";
	private List<Player> players = new ArrayList<>();
	private long aboTime = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin() {
		return level > 3;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public long getAboTime() {
		return aboTime;
	}

	public void setAboTime(long aboTime) {
		this.aboTime = aboTime;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public void addPlayer(Player param1) {
        players.add(param1);
	}
}
