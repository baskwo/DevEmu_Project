package org.devemu.sql.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.devemu.sql.dao.DAO;
import org.devemu.sql.entity.ExpStep;

public class ExpManager {
	public static Number getCount() {
		return DAO.getExpDAO().getSize();
	}
	
	public static ExpStep getExpStepByLevel(int arg0) {
		return DAO.getExpDAO().find(arg0);
	}
	
	public static ExpStep create(ResultSet set) {
        try {
            ExpStep acc = new ExpStep();
            acc.setLevel(set.getInt("level"));
            acc.setPlayer(set.getLong("player"));
            acc.setAlignement(set.getLong("alignement"));
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
