package org.devemu.sql.entity;

public class ExpStep {
	private int level = 0;//Level
	private long player = 0;
	private long alignement = 0;
	
	public int getId() {
		return level;
	}
	public void setId(int id) {
		this.level = id;
	}
	public long getPlayer() {
		return player;
	}
	public void setPlayer(long player) {
		this.player = player;
	}
	public long getAlignement() {
		return alignement;
	}
	public void setAlignement(long alignement) {
		this.alignement = alignement;
	}
}
