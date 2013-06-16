package org.devemu.sql.entity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.devemu.sql.entity.Player;

@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface PlayerMapper {
	final String SELECT_BY_ACCOUNT_ID = "SELECT * FROM players WHERE accountId = #{id}";
	
	@Select(SELECT_BY_ACCOUNT_ID)
    List<Player> getPlayersByAccountId(@Param("id") String id);
}
