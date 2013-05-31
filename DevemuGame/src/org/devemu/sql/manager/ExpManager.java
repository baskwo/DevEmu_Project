package org.devemu.sql.manager;

import org.devemu.sql.entity.ExpStep;

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
