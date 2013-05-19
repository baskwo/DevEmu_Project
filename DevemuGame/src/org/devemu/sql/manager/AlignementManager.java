package org.devemu.sql.manager;

import org.devemu.program.Main;
import org.devemu.sql.entity.Alignement;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

public class AlignementManager {
	public static int getNextId() {
		Session loc1 = Main.getSqlSession();
		Object loc3 = loc1.createCriteria(Alignement.class).setProjection(Projections.max("id")).uniqueResult();
		int loc2 = 0;
		if(loc3 != null)
			loc2 = (int) loc3;
		loc1.close();
		return (loc2 + 1);
	}
}
