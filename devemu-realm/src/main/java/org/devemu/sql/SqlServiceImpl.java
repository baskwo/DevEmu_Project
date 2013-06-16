package org.devemu.sql;

import org.devemu.sql.SqlService;
import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.mapper.AccountMapper;
import org.devemu.sql.entity.mapper.BanMapper;
import org.devemu.sql.entity.mapper.PlayerMapper;
import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;

public class SqlServiceImpl implements SqlService {

	@Inject
    private AccountMapper accMapper;
	
	@Inject
    private BanMapper banMapper;
	
	@Inject
	private PlayerMapper playerMapper;
	
	@Transactional
	public Account findAccountById(String accId) {
		return this.accMapper.getAccountById(accId);
	}
	
	@Transactional
	public Account findAccountByName(String accName) {
		return this.accMapper.getAccountByName(accName);
	}
}
