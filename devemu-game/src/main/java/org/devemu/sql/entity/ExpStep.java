package org.devemu.sql.entity;

public class ExpStep {
	private int level = 0;//Level
	private long player = 0;
	private long alignement = 0;
	
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
