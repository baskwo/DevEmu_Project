package org.devemu.sql.entity.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Many;
import org.devemu.sql.entity.Account;

@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
public interface AccountMapper {
	
    final String SELECT_BY_ID = "SELECT * FROM accounts WHERE id = #{accId}";
    final String SELECT_BY_NAME = "SELECT * FROM accounts WHERE name = #{accName}";
	
	@Select(SELECT_BY_ID)
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "players", column = "id", javaType=ArrayList.class, many=@Many(select = "org.devemu.sql.entity.mapper.PlayerMapper.getPlayersByAccountId"))
	})
    Account getAccountById(@Param("accId") String accId);
	
	@Select(SELECT_BY_NAME)
	@Results({
		@Result(property = "id", column = "id"),
        @Result(property = "players", column = "id", javaType=ArrayList.class, many=@Many(select = "org.devemu.sql.entity.mapper.PlayerMapper.getPlayersByAccountId"))
    })
    Account getAccountByName(@Param("accName") String accName);
}
