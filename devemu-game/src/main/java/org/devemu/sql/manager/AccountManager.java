package org.devemu.sql.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.devemu.program.Main;
import org.devemu.sql.dao.DAO;
import org.devemu.sql.entity.Account;

public class AccountManager {
	public static Account findByName(String arg0) {
		Account loc3 = DAO.getAccountDAO().find(arg0);
		return loc3;
	}
	
	public static Account findById(int arg0) {
		Account loc2 = DAO.getAccountDAO().find(arg0);
		return loc2;
	}
	
	public static long getAboTime(Account arg0) {
		if(arg0.getAboTime() == 0)
			return 0;
		return (arg0.getAboTime() - System.currentTimeMillis());
	}
	
	public static void removePlayer(Account arg1,int arg2) {
		if(arg1.getPlayers().contains(arg2))
			arg1.getPlayers().remove(arg2);
	}
	
	public static Account create(ResultSet set) {
        try {
            Account acc = new Account();
            acc.setGuid(set.getInt("id"));
            acc.setName(set.getString("name"));
            acc.setPassword(set.getString("password"));
            acc.setPseudo(set.getString("pseudo"));
            acc.setQuestion(set.getString("question"));
            acc.setAboTime(set.getLong("aboTime"));
            String s = set.getString("players");
            String[] s1 = s.split(";");
            for (String s2 : s1) {
                String[] s3 = StringUtils.split(s2, ':');
                if(Integer.parseInt(s3[1]) == Main.getGuid())
                	acc.getPlayers().add(Integer.parseInt(s3[0]));
            }
            return acc;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
	
	public static Account create(String[] set) {
        Account acc = new Account();
        acc.setGuid(Integer.parseInt(set[0]));
        acc.setName(set[1]);
        acc.setPassword(set[2]);
        acc.setPseudo(set[3]);
        acc.setQuestion(set[4]);
        acc.setAboTime(Long.parseLong(set[5]));
        String s = set[6];
        String[] s1 = StringUtils.split(s, ';');
        for (String s2 : s1) {
            String[] s3 = StringUtils.split(s2, ':');
            if(Integer.parseInt(s3[1]) == Main.getGuid())
            	acc.getPlayers().add(Integer.parseInt(s3[0]));
        }
        return acc;
    }
}
