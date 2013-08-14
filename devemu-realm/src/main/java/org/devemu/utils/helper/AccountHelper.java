package org.devemu.utils.helper;

import org.devemu.sql.entity.Account;

public class AccountHelper {
	
	public long getAboTime(Account acc) {
		if(acc.getAboTime() == 0)
			return 0;
		return (acc.getAboTime() - System.currentTimeMillis());
	}
}
