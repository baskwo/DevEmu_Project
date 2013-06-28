package org.devemu.sql.manager;

import org.devemu.sql.entity.Maps;
import org.devemu.sql.entity.Player;

public class MapsManager {
	public static void removeMarchantOnMaps(Maps arg0, Player arg1) {
		arg0.getMarchantsOnMaps().remove((Object)arg1);
		/*Packet loc1 = new Packet();
		loc1.setIdentificator("GD");
		loc1.getParam().add("-" + arg1);
		
		for(GameClient loc2 : arg0.getAllClients())
			loc2.write(loc1.toString());*/
	}
	
	public static void addPlayerOnMaps(Maps arg0, Player arg1) {
		arg0.getPlayersOnMaps().add(arg1);
		/*Packet loc1 = new Packet();
		loc1.setIdentificator("GM");
		loc1.getParam().add("+" + PlayerManager.getGMData(arg1));
		
		for(GameClient loc2 : arg0.getAllClients())
			loc2.write(loc1.toString());*/
	}
}
