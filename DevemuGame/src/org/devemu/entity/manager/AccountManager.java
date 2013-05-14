package org.devemu.entity.manager;

import org.devemu.entity.Account;
import org.devemu.program.Main;
import org.hibernate.Query;
import org.hibernate.Session;

public class AccountManager {
	public static Account findByName(String arg0) {
		Session loc1 = Main.getSqlSession();
		Query loc2 = loc1.getNamedQuery("findAccountByName");
		loc2.setParameter("name", arg0);
		Account loc3 = (Account) loc2.uniqueResult();
		loc1.close();
		return loc3;
	}
	
	public static Account findById(int arg0) {
		Session loc1 = Main.getSqlSession();
		Account loc2 = (Account) loc1.get(Account.class, arg0);
		loc1.close();
		return loc2;
	}
	
	public static long getAboTime(Account arg0) {
		if(arg0.getAboTime() == 0)
			return 0;
		return (arg0.getAboTime() - System.currentTimeMillis());
	}
	
	public static void removePlayer(Account arg1,int arg2) {
		for(int i = 0; i < arg1.getPlayers().size(); i++) {
			if(arg1.getPlayers().get(i).getGuid() == arg2)
				arg1.getPlayers().remove(i);
		}
	}
}
