package org.devemu.sql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.devemu.sql.entity.Player;

@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface PlayerMapper {
	final String SELECT_BY_ID = "SELECT * FROM players WHERE guid = #{guid}";
	final String DELETE_BY_ID = "DELETE FROM players WHERE guid = #{guid}";
	final String GET_MAX_ID = "SELECT MAX(guid) FROM players";
	final String INSERT = "INSERT INTO players VALUES (#{player})";
	final String SELECT_BY_ACCOUNT_ID = "SELECT * FROM players WHERE accountId = #{id}";

	
	@Select(SELECT_BY_ID)
    Player getPlayerById(@Param("guid") String playerId);
	
	@Delete(DELETE_BY_ID)
	@Options(flushCache=true)
	int deleteById(@Param("guid") String playerId);
	
	@Insert(INSERT)
	int insertPlayer(@Param("player") Player player); 
	
	@Select(GET_MAX_ID)
	int getNextPlayerId();
	
	@Select(SELECT_BY_ACCOUNT_ID)
    List<Player> getPlayersByAccountId(@Param("id") String id);
}
