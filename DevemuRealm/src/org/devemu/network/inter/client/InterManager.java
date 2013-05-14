package org.devemu.network.inter.client;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.InterId;
import org.devemu.network.protocol.ServerPacket;
import org.devemu.network.server.client.ClientManager;
import org.devemu.utils.enums.ServerPop;
import org.devemu.utils.enums.ServerState;

public class InterManager {
	public static void onInfo(IoSession arg0, ServerPacket arg1) {
		int loc1 = arg1.getData().getInt();
		InterClient loc2 = ClientFactory.get(loc1);
		loc2.setState(ServerState.get(arg1.getData().get()));
		loc2.setPopulation(ServerPop.get(arg1.getData().get()));
		loc2.setIp(arg1.readString());
		loc2.setPort(arg1.getData().getInt());
		loc2.setAllowNoSubscribe(arg1.getData().get() == 1 ? true : false);
		loc2.setSession(arg0);
		arg0.setAttribute("client", loc2);
		
		ServerPacket loc3 = new ServerPacket();
		loc3.setId(InterId.INFO_AGREED.getId());
		loc2.write(loc3.toBuff());
		
		ClientManager.refreshServer();
	}
	
	public static void onWaitingAgreed(InterClient arg0,ServerPacket arg1) {
		int loc1 = arg1.getData().getInt();
		ClientManager.onTransfer(loc1, arg0);
	}
}