package org.devemu.sql.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.devemu.sql.entity.Stats;

@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface StatsMapper {
	final String SELECT_BY_PLAYER_ID = "SELECT * FROM stats WHERE playerId = #{id}";
	
	@Select(SELECT_BY_PLAYER_ID)
	@MapKey("statsId")
	Map<Integer,Stats> getStatsByPlayerId(@Param("id") String id);
}
