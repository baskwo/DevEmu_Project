package org.devemu.sql.entity.mapper;

import org.apache.ibatis.annotations.CacheNamespace;

@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface BanMapper {

}
