package org.devemu.sql.entity;

import java.io.Serializable;

public class Ban implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String username = "";
	private long banTime = 0;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getBanTime() {
		return banTime;
	}
	public void setBanTime(long banTime) {
		this.banTime = banTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
