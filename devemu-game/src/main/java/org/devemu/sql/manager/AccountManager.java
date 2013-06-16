package org.devemu.sql.manager;

import org.devemu.sql.entity.Account;

public class AccountManager {
	public static long getAboTime(Account arg0) {
		if(arg0.getAboTime() == 0)
			return 0;
		return (arg0.getAboTime() - System.currentTimeMillis());
	}
	
	public static void removePlayer(Account arg1,int arg2) {
		if(arg1.getPlayers().contains(arg2)) {
			for(int i = 0; i < arg1.getPlayers().size(); i++) {
				if(arg1.getPlayers().get(i).getGuid() == arg2) {
					arg1.getPlayers().remove(i);
				}
			}
		}
	}
}
