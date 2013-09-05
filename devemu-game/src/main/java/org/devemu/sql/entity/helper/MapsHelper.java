package org.devemu.sql.entity.helper;

import org.devemu.sql.entity.Maps;
import org.devemu.sql.entity.Player;

public class MapsHelper {
	public void removeMarchantOnMaps(Maps map, Player p) {
		map.getMarchantsOnMaps().remove((Object)p);
		/*Packet loc1 = new Packet();
		loc1.setIdentificator("GD");
		loc1.getParam().add("-" + arg1);
		
		for(GameClient loc2 : arg0.getAllClients())
			loc2.write(loc1.toString());*/
	}
	
	public void addPlayerOnMaps(Maps map, Player p) {
		map.getPlayersOnMaps().add(p);
		/*Packet loc1 = new Packet();
		loc1.setIdentificator("GM");
		loc1.getParam().add("+" + PlayerManager.getGMData(arg1));
		
		for(GameClient loc2 : arg0.getAllClients())
			loc2.write(loc1.toString());*/
	}
}
