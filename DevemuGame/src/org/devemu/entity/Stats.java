package org.devemu.entity;

public class Stats {
	private int id = 0;
	private int statsId = 0;
	private int value = 0;
	
	public Stats() {
	}
	
	public Stats(int arg0, int arg1, int arg2) {
		id = arg0;
		statsId = arg1;
		value = arg2;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
