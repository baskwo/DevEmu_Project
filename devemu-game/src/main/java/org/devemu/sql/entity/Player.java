package org.devemu.sql.entity;

import java.util.HashMap;
import java.util.Map;

public class Player {
	private int guid = 0;
	private String name = "";
	private int level = 1;//TODO: Better handler of level and exp?
	private int gfx = -1;
	private int[] colors = {-1,-1,-1};
	//private Map<Byte,Item> items = new HashMap<Byte,Item>();
	private boolean marchant = false;
	private boolean dead = false;
	private int countDead = 0;
	private int classe = 0;
	private boolean sexe = false;
	private int gameGuid = 0;
	private long xp = 0;
	private Map<Integer,Stats> stats = new HashMap<Integer,Stats>();
	private long kamas = 0;
	private int capitals = 0;
	private int spellPoints = 0;
	private int pdv = 0;
	private int energy = 0;
	private Alignement align = new Alignement();
	private boolean isShowingWings = false;

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

	public int getGfx() {
		return gfx;
	}

	public void setGfx(int gfx) {
		this.gfx = gfx;
	}

	public int[] getColors() {
		return colors;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}

	public int getCountDead() {
		return countDead;
	}

	public void setCountDead(int countDead) {
		this.countDead = countDead;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isMarchant() {
		return marchant;
	}

	public void setMarchant(boolean marchant) {
		this.marchant = marchant;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getClasse() {
		return classe;
	}

	public void setClasse(int classe) {
		this.classe = classe;
	}

	public boolean isSexe() {
		return sexe;
	}

	public void setSexe(boolean sexe) {
		this.sexe = sexe;
	}

	public int getGameGuid() {
		return gameGuid;
	}

	public void setGameGuid(int gameGuid) {
		this.gameGuid = gameGuid;
	}

	public long getXp() {
		return xp;
	}

	public void setXp(long xp) {
		this.xp = xp;
	}

	public Map<Integer,Stats> getStats() {
		return stats;
	}

	public void setStats(Map<Integer,Stats> stats) {
		this.stats = stats;
	}

	public long getKamas() {
		return kamas;
	}

	public void setKamas(long kamas) {
		this.kamas = kamas;
	}

	public int getCapitals() {
		return capitals;
	}

	public void setCapitals(int capitals) {
		this.capitals = capitals;
	}

	public int getSpellPoints() {
		return spellPoints;
	}

	public void setSpellPoints(int spellPoints) {
		this.spellPoints = spellPoints;
	}

	public int getPdv() {
		return pdv;
	}

	public void setPdv(int pdv) {
		this.pdv = pdv;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public boolean isShowingWings() {
		return isShowingWings;
	}

	public void setShowingWings(boolean isShowingWings) {
		this.isShowingWings = isShowingWings;
	}

	public Alignement getAlign() {
		return align;
	}

	public void setAlign(Alignement align) {
		this.align = align;
	}
}
