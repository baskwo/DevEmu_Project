package org.devemu.utils.helper;

import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.Player;

public class AccountHelper {
	public long getAboTime(Account acc) {
		if(acc.getAboTime() == 0)
			return 0;
		return (acc.getAboTime() - System.currentTimeMillis());
	}
	
	public void removePlayer(Account acc,int pId) {
		if(acc.getPlayers().contains(pId)) {
			for(int i = 0; i < acc.getPlayers().size(); i++) {
				if(acc.getPlayers().get(i).getGuid() == pId) {
					acc.getPlayers().remove(i);
				}
			}
		}
	}
	
	public Player getPlayer(int pId, Account acc) {
		for(Player p : acc.getPlayers()) {
			if(p.getGuid() == pId)
				return p;
		}
		return null;
	}
}
