package org.devemu.sql.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "alignements")
public class Alignment implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "guid")
	private int guid = 0;
	private byte ordre = 0;
	private int level = 0;
	private int honor = 0;
	private int deshonor = 0;
	private int grade = 0;
	
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
	public int getGuid() {
		return guid;
	}
	public void setGuid(int guid) {
		this.guid = guid;
	}
	
}
