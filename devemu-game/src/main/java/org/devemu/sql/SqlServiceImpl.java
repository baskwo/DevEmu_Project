package org.devemu.sql;

import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.Maps;
import org.devemu.sql.entity.Player;
import org.devemu.sql.mapper.AccountMapper;
import org.devemu.sql.mapper.MapsMapper;
import org.devemu.sql.mapper.PlayerMapper;
import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;

public class SqlServiceImpl implements SqlService{
	
	@Inject
	private AccountMapper accMapper;
	
	@Inject
	private PlayerMapper playerMapper;
	
	@Inject
	private MapsMapper mapsMapper;

	@Transactional
	public Account findAccountById(String accId) {
		Account acc = this.accMapper.getAccountById(accId);
		return acc;
	}

	@Transactional
	public Player findPlayerById(String playerId) {
		return this.playerMapper.getPlayerById(playerId);
	}
	
	@Transactional
	public Maps findMapsById(String mapsId) {
		return this.mapsMapper.getMapsById(mapsId);
	}

	@Transactional
	public int deletePlayerById(String playerId) {
		return this.playerMapper.deleteById(playerId);
	}

	@Transactional
	public int getNextPlayerId() {
		return this.playerMapper.getNextPlayerId() + 1;
	}

	@Transactional
	public int insertPlayer(Player player) {
		return this.playerMapper.insertPlayer(player);
	}
}
