package org.devemu.sql.entity.helper;

import org.devemu.sql.entity.Ban;

public class BanHelper {
	
	public long getBanTime(Ban ban) {
		if(ban.getBanTime() == 0)
			return 0;
		return (ban.getBanTime() - System.currentTimeMillis());
	}
}
