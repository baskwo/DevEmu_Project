package org.devemu.sql.manager;

import org.devemu.sql.entity.Ban;

public class BanManager {
	public static long getBanTime(Ban arg0) {
		if(arg0.getBanTime() == 0)
			return 0;
		return (arg0.getBanTime() - System.currentTimeMillis());
	}
}
