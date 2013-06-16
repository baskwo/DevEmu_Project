package org.devemu.sql;

import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.Maps;
import org.devemu.sql.entity.Player;

public interface SqlService {
	public Account findAccountById(String accId);
	public Player findPlayerById(String playerId);
	public Maps findMapsById(String mapsId);
	public int deletePlayerById(String playerId);
	public int getNextPlayerId();
	public int insertPlayer(Player player);
}
