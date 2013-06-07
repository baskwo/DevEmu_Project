package org.devemu.sql.entity;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

@SuppressWarnings("UnusedDeclaration")
public class Account {
	private int guid = 0;
	private String name = "";
	private String password = "";
	private int level = 0;
	private String pseudo = "";
	private String question = "";
	private Multiset<Integer> players = TreeMultiset.create();
	private long aboTime = 0;

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
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

	public Multiset<Integer> getPlayers() {
		return players;
	}

	public void setPlayers(Multiset<Integer> players) {
		this.players = players;
	}
	
	public void addPlayer(int param1) {
        players.add(param1);
	}
}
