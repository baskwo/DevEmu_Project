package org.devemu.sql.entity;

public class Player {
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
