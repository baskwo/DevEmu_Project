package org.devemu.sql.entity.manager;

import org.devemu.sql.entity.Account;

public class AccountManager {
	
	public static long getAboTime(Account acc) {
		if(acc.getAboTime() == 0)
			return 0;
		return (acc.getAboTime() - System.currentTimeMillis());
	}
}
