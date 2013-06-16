package org.devemu.sql;

import org.devemu.sql.entity.Account;

public interface SqlService {
	public Account findAccountById(String accId);
	public Account findAccountByName(String accName);
}
