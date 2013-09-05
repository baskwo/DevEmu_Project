package org.devemu.sql.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "guid")
	private int guid = 0;
	private int gameGuid = 0;

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public int getGameGuid() {
		return gameGuid;
	}

	public void setGameGuid(int gameGuid) {
		this.gameGuid = gameGuid;
	}
}
