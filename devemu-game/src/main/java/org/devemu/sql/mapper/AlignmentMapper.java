package org.devemu.sql.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.devemu.sql.entity.Alignment;

@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface AlignmentMapper {
	final String SELECT_BY_PLAYER_ID = "SELECT * FROM alignments WHERE playerId = #{id}";
	
	@Select(SELECT_BY_PLAYER_ID)
    Alignment getAlignmentByPlayerId(@Param("id") String playerId);
}
