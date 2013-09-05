package org.devemu.sql.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.devemu.network.server.client.GameClient;

import com.google.common.collect.Maps;

@Entity
@Table(name = "players")
public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "guid")
	private int guid = 0;
	private String name = "";
	private int level = 1;//TODO: Better handler of level and exp?
	private int gfx = -1;
	private String colors = "-1,-1,-1";
	//private Map<Byte,Item> items = new HashMap<Byte,Item>();
	private boolean marchant = false;
	private boolean dead = false;
	private int countDead = 0;
	private int classe = 0;
	private boolean sexe = false;
	private int gameGuid = 0;
	private long xp = 0;
	@OneToMany(mappedBy="player", fetch = FetchType.EAGER)
	@MapKey(name="statsId")
	private Map<Integer,Stats> stats = Maps.newHashMap();
	private long kamas = 0;
	private int capitals = 0;
	private int spellPoints = 0;
	private int pdv = 0;
	private int energy = 0;
	@OneToOne(fetch = FetchType.EAGER)
	private Alignment align = new Alignment();
	private boolean isShowingWings = false;
	private int mapsId = 0;
	@Transient
	private GameClient client = null;
	private int cell = 0;
	private byte orientation = 0;
	private int size = 100;

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

	public Alignment getAlign() {
		return align;
	}

	public void setAlign(Alignment align) {
		this.align = align;
	}

	public int getMapsId() {
		return mapsId;
	}

	public void setMapsId(int mapsId) {
		this.mapsId = mapsId;
	}

	public GameClient getClient() {
		return client;
	}

	public void setClient(GameClient client) {
		this.client = client;
	}

	public int getCell() {
		return cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}

	public byte getOrientation() {
		return orientation;
	}

	public void setOrientation(byte orientation) {
		this.orientation = orientation;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}
	
	public int getColor(int index) {
		return Integer.parseInt(colors.split(",")[index]);
	}
}
