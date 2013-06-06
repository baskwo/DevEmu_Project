package org.devemu.network.server.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.mina.core.session.IoSession;
import org.devemu.network.InterId;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.protocol.Packet;
import org.devemu.network.protocol.ServerPacket;
import org.devemu.network.server.RealmServer;
import org.devemu.network.server.client.RealmClient.State;
import org.devemu.program.Main;
import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.manager.AccountManager;
import org.devemu.utils.Crypt;
import org.devemu.utils.config.ConfigEnum;
import org.devemu.utils.queue.QueueSelector;

public class ClientManager {
	public static Map<Integer,RealmClient> waitings = new TreeMap<Integer,RealmClient>();
	
	public static void onVersion(String arg1, RealmClient arg2) {
		if(arg1.equalsIgnoreCase((String)Main.getConfigValue(ConfigEnum.VERSION))) {
			arg2.setState(State.ACCOUNT);
		}else{
			Packet loc1 = new Packet();
			loc1.setIdentificator("Al");
			loc1.setFirstParam("Ev");
		}
	}
	
	public static void onAccount(String arg1, RealmClient arg2) {
		String loc1 = arg1.split("\n")[0];//Username
		String loc2 = arg1.split("\n")[1];//HashPass
		
		Account loc3 = AccountManager.findByName(loc1);
		Packet loc5 = new Packet();
		loc5.setIdentificator("Al");
		loc5.setFirstParam("Ef");
		if(loc3 != null) {
			String loc4 = Crypt.cryptAnkama(loc3.getPassword(), arg2.getSalt());
			if(loc2.equals(loc4)) {
				arg2.setAcc(loc3);
				arg2.setState(State.SERVER);
				QueueSelector.addToQueue(arg2);
			}else{
				arg2.write(loc5.toString());
			}
		}else {
			arg2.write(loc5.toString());
		}
	}
	
	public static void onQueue(RealmClient arg1) {
		Packet loc2 = new Packet();
		loc2.setIdentificator("Af");
		loc2.setFirstParam(""+arg1.getQueue());
		List<String> loc3 = loc2.getParam();
		loc3.add(""+QueueSelector.getTotAbo());
			loc3.add(""+QueueSelector.getTotNonAbo());
			loc3.add("0"); //Subscriber
			loc3.add("1"); //QueueID
			loc2.setParam(loc3);
			arg1.write(loc2.toString());
	}
	
	public static void onStat(RealmClient arg1) {
		Packet loc1 = new Packet();
		loc1.setIdentificator("Al");
		loc1.setFirstParam("k" + (arg1.getAcc().isAdmin() ? 1 : 0));
		
		Packet loc2 = new Packet();
		loc2.setIdentificator("Ad");
		loc2.setFirstParam(arg1.getAcc().getPseudo());
		
		Packet loc3 = new Packet();
		loc3.setIdentificator("Ac");
		loc3.setFirstParam((String)Main.getConfigValue(ConfigEnum.COMMUNITY));
		
		Packet loc4 = new Packet();
		loc4.setIdentificator("AQ");
		String loc6 = arg1.getAcc().getQuestion().replace(' ', '+');
		loc4.setFirstParam(loc6);
		
		Packet loc5 = new Packet();
		loc5.setIdentificator("AH");
		for(InterClient loc8 : ClientFactory.getClients().values()) {
			if(loc5.getFirstParam().isEmpty())
				loc5.setFirstParam(loc8.getGuid() + ";" + loc8.getState().getState() + ";" + loc8.getPopulation().getPopulation() + ";" + (loc8.isAllowNoSubscribe() ? 1 : 0));
			else
				loc5.getParam().add(loc8.getGuid() + ";" + loc8.getState().getState() + ";" + loc8.getPopulation().getPopulation() + ";" + (loc8.isAllowNoSubscribe() ? 1 : 0));
		}
		
		List<Packet> loc7 = new ArrayList<Packet>();
		loc7.add(loc1);
		loc7.add(loc2);
		loc7.add(loc3);
		loc7.add(loc4);
		loc7.add(loc5);
		
		arg1.write(loc7);
	}
	
	public static void onServersList(RealmClient arg1) {
		Packet loc1 = new Packet();
		loc1.setIdentificator("Ax");
		loc1.setFirstParam("K" + AccountManager.getAboTime(arg1.getAcc()));

		List<String> loc2 = new ArrayList<String>();
		for(Entry<Integer,Byte> loc7 : arg1.getAcc().getPlayers().entrySet()) {
			loc2.add(loc7.getKey() + "," + loc7.getValue());
		}
		loc1.setParam(loc2);
		arg1.write(loc1.toString());
	}
	
	
	public static void onConnection(Packet arg1, RealmClient arg2) {
		int loc1 = Integer.parseInt(arg1.getFirstParam());
		InterClient loc2 = ClientFactory.get(loc1);
		waitings.put(arg2.getAcc().getGuid(), arg2);
		
		ServerPacket loc3 = new ServerPacket();
		loc3.setId(InterId.ACC_WAITING.getId());
		loc3.getData().putInt(arg2.getAcc().getGuid());
		loc2.write(loc3.toBuff());
	}
	
	public static void onTransfer(int arg0,InterClient arg1) {
		RealmClient loc1 = waitings.get(arg0);
		waitings.remove(arg0);
		//TODO: UseCryptedIp
		Packet loc3 = new Packet();
		loc3.setIdentificator("AY");
		loc3.setFirstParam("K" + arg1.getIp() + ":" + arg1.getPort() + ";" + arg1.getGuid());
		loc1.write(loc3.toString());
	}
	
	public static void refreshServer() {
		Packet loc5 = new Packet();
		loc5.setIdentificator("AH");
		for(InterClient loc8 : ClientFactory.getClients().values()) {
			if(loc5.getFirstParam().isEmpty())
				loc5.setFirstParam(loc8.getGuid() + ";" + loc8.getState().getState() + ";" + loc8.getPopulation().getPopulation() + ";" + (loc8.isAllowNoSubscribe() ? 1 : 0));
			else
				loc5.getParam().add(loc8.getGuid() + ";" + loc8.getState().getState() + ";" + loc8.getPopulation().getPopulation() + ";" + (loc8.isAllowNoSubscribe() ? 1 : 0));
		}
		for(IoSession loc1 : RealmServer.getInstance().getAcceptor().getManagedSessions().values()) {
			if(loc1.containsAttribute("client")) {
				if(loc1.getAttribute("client") instanceof RealmClient) {
					RealmClient loc2 = (RealmClient)loc1.getAttribute("client");
					loc2.write(loc5.toString());
				}
			}
		}
	}
}
