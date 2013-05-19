package org.devemu.sql.manager;

import org.devemu.program.Main;
import org.devemu.sql.entity.Stats;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

public class StatsManager {
	public static int getNextId() {
		Session loc1 = Main.getSqlSession();
		Object loc3 = loc1.createCriteria(Stats.class).setProjection(Projections.max("id")).uniqueResult();
		int loc2 = 0;
		if(loc3 != null)
			loc2 = (int) loc3;
		loc1.close();
		return (loc2 + 1);
	}
}
