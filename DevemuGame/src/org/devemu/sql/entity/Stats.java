package org.devemu.sql.entity;

public class Stats {
	private int statsId = 0;
	private int value = 0;
	
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
}
