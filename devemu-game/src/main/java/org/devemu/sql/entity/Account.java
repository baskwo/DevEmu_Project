package org.devemu.sql.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String name = "";
	private String password = "";
	private int level = 0;
	private String pseudo = "";
	private String question = "";
	private List<Player> players = Lists.newArrayList();
	private long aboTime = 0;

	public int getGuid() {
		return id;
	}

	public void setGuid(int guid) {
		this.id = guid;
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
		return level > 3 ? true : false;
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

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public long getAboTime() {
		return aboTime;
	}

	public void setAboTime(long aboTime) {
		this.aboTime = aboTime;
	}
}
