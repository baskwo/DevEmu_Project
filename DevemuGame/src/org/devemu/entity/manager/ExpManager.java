package org.devemu.entity.manager;

import org.devemu.entity.ExpStep;
import org.devemu.program.Main;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

public class ExpManager {
	public static Number getCount() {
		Session loc1 = Main.getSqlSession();
		Number loc2 = (Number) loc1.createCriteria(ExpStep.class).setProjection(Projections.rowCount()).uniqueResult();
		loc1.close();
		return loc2;
	}
	
	public static ExpStep getExpStepByLevel(int arg0) {
		Session loc0 = Main.getSqlSession();
		ExpStep loc1 = (ExpStep) loc0.get(ExpStep.class, arg0);
		loc0.close();
		return loc1;
	}
}
