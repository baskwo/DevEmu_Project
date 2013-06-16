package org.devemu.sql.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.devemu.sql.entity.Maps;

@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface MapsMapper {
	final String SELECT_BY_ID = "SELECT * FROM maps WHERE id = #{mapsId}";
	
	@Select(SELECT_BY_ID)
    Maps getMapsById(@Param("mapsId") String mapsId);
}
