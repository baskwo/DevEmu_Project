package org.devemu.entity.manager;

import java.util.ArrayList;
import java.util.List;

import org.devemu.entity.Ban;
import org.devemu.program.Main;
import org.hibernate.Query;
import org.hibernate.Session;

public class BanManager {
	public static List<String> getAllBan() {
		List<String> loc0 = new ArrayList<String>();
		Session loc1 = Main.getSqlSession();
		Query loc2 = loc1.createQuery("from org.ishina.entity.Ban");
		for(Object loc3 : loc2.list()) {
			Ban loc4 = (Ban)loc3;
			loc0.add(loc4.getUsername());
		}
		return loc0;
	}
	
	public static long getBanTime(Ban arg0) {
		if(arg0.getBanTime() == 0)
			return 0;
		return (arg0.getBanTime() - System.currentTimeMillis());
	}
}
