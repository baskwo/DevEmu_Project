package org.devemu.sql.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.devemu.program.Main;
import org.devemu.sql.entity.Account;
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
	
	public static ExpStep create(ResultSet set) {
        try {
            ExpStep acc = new ExpStep();
            acc.setLevel(set.getInt("id"));
            acc.setPlayer(set.getLong("name"));
            acc.setAlignement(set.getLong("password"));
            return acc;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
	
	public static ExpStep create(String[] set) {
        ExpStep acc = new ExpStep();
		acc.setLevel(Integer.parseInt(set[0]));
		acc.setPlayer(Long.parseLong(set[1]));
		acc.setAlignement(Long.parseLong(set[2]));
		return acc;
    }
}
