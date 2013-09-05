package org.devemu.sql.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stats")
public class Stats {
	@Id
	@Basic(optional = false)
	@Column(name = "guid")
	private int guid = 0;
	private int statsId = 0;
	private int value = 0;
	@ManyToOne(fetch = FetchType.EAGER)
	private Player player = null;
	
	public Stats() {
	}
	
	public Stats(int arg1, int arg2) {
		statsId = arg1;
		value = arg2;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getStatsId() {
		return statsId;
	}
	public void setStatsId(int statsId) {
		this.statsId = statsId;
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
