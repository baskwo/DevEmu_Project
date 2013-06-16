package org.devemu.sql.entity.manager;

import org.devemu.sql.entity.Account;

public class AccountManager {
	
	public static long getAboTime(Account arg0) {
		if(arg0.getAboTime() == 0)
			return 0;
		return (arg0.getAboTime() - System.currentTimeMillis());
	}
}
