package org.devemu.entity;

public class Alignement {
	private int id = 0;
	private byte ordre = 0;
	private int level = 0;
	private int honor = 0;
	private int deshonor = 0;
	private int grade = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte getOrdre() {
		return ordre;
	}
	public void setOrdre(byte ordre) {
		this.ordre = ordre;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getHonor() {
		return honor;
	}
	public void setHonor(int honor) {
		this.honor = honor;
	}
	public int getDeshonor() {
		return deshonor;
	}
	public void setDeshonor(int deshonor) {
		this.deshonor = deshonor;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
}
