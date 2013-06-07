package org.devemu.sql.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.devemu.network.protocol.Packet;
import org.devemu.network.server.client.GameClient;
import org.devemu.sql.dao.DAO;
import org.devemu.sql.entity.Maps;

public class MapsManager {
	public static Maps get(int id) {
		return DAO.getMapsDAO().find(id);
	}
	
	public static void removeMarchantOnMaps(Maps arg0, int arg1) {
		arg0.getMarchantsOnMaps().remove((Object)arg1);
		Packet loc1 = new Packet();
		loc1.setIdentificator("GD");
		loc1.getParam().add("-" + arg1);
		
		for(GameClient loc2 : arg0.getAllClients())
			loc2.write(loc1.toString());
	}
	
	public static void addPlayerOnMaps(Maps arg0, int arg1) {
		arg0.getPlayersOnMaps().add(arg1);
		Packet loc1 = new Packet();
		loc1.setIdentificator("GM");
		loc1.getParam().add("+" + PlayerManager.getGMData(DAO.getPlayerDAO().find(arg1)));
		
		for(GameClient loc2 : arg0.getAllClients())
			loc2.write(loc1.toString());
	}
	
	public static Maps create(ResultSet set) {
        try {
            Maps maps = new Maps();
            maps.setId(set.getInt("id"));
            maps.setDate(set.getString("date"));
            maps.setKey(set.getString("key"));

            return maps;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
	
	public static Maps create(String[] set) {
        Maps maps = new Maps();
        maps.setId(Integer.parseInt(set[0]));
        maps.setDate(set[1]);
        maps.setKey(set[2]);
        return maps;
    }
}
