package org.devemu.sql.manager;

import org.devemu.sql.entity.Ban;

public class BanManager {
	public static long getBanTime(Ban ban) {
		if(ban.getBanTime() == 0)
			return 0;
		return (ban.getBanTime() - System.currentTimeMillis());
	}
}
