package org.devemu.sql.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.devemu.sql.dao.DAO;
import org.devemu.sql.entity.Ban;

public class BanManager {
	public static List<String> getAllBan() {
		List<String> loc0 = new ArrayList<String>();
		for(Ban loc1 : DAO.getBanDAO().findAll()) {
			loc0.add(loc1.getUsername());
		}
		return loc0;
	}
	
	public static long getBanTime(Ban arg0) {
		if(arg0.getBanTime() == 0)
			return 0;
		return (arg0.getBanTime() - System.currentTimeMillis());
	}
	
	public static Ban create(ResultSet set) {
        try {
            Ban acc = new Ban();
            acc.setId(set.getInt("id"));
            acc.setUsername(set.getString("username"));
            acc.setBanTime(set.getLong("banTime"));
            return acc;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
	
	public static Ban create(String[] set) {
        Ban acc = new Ban();
		acc.setId(Integer.parseInt(set[0]));
		acc.setUsername(set[1]);
		acc.setBanTime(Long.parseLong(set[2]));
		return acc;
    }
}
