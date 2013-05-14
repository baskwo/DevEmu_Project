package org.devemu.network.inter.client;

import java.util.ArrayList;
import java.util.List;

import org.devemu.network.InterId;
import org.devemu.network.protocol.ServerPacket;
import org.devemu.program.Main;
import org.devemu.utils.config.ConfigEnum;

public class InterManager {
	public static List<Integer> waitings = new ArrayList<Integer>();
	
	public static void onConnect(InterClient arg0) {
		ServerPacket loc1 = new ServerPacket();
		loc1.setId(InterId.INFO.getId());
		loc1.getData().putInt(Main.getGuid());
		loc1.getData().put(Main.getState().getState());
		loc1.getData().put(Main.getPopulation().getPopulation());
		loc1.writeString((String)Main.getConfigValue(ConfigEnum.GAME_IP));
		loc1.getData().putInt(Integer.parseInt((String)Main.getConfigValue(ConfigEnum.GAME_PORT)));
		loc1.getData().put((byte)(Main.isAllowNoSubscribe() ? 1 : 0));
		arg0.write(loc1.toBuff());
	}
	
	public static void onWaiting(InterClient arg0, ServerPacket arg1) {
		int loc2 = arg1.getData().getInt();
		waitings.add(loc2);
		
		ServerPacket loc1 = new ServerPacket();
		loc1.setId(InterId.ACC_WAITING_AGREED.getId());
		loc1.getData().putInt(loc2);
		arg0.write(loc1.toBuff());
	}
}
