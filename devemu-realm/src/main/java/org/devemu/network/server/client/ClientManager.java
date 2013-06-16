package org.devemu.network.server.client;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

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
import org.devemu.sql.entity.Player;
import org.devemu.sql.entity.manager.AccountManager;
import org.devemu.utils.Crypt;
import org.devemu.utils.queue.QueueSelector;

import java.util.*;

public class ClientManager {
	public static Map<Integer,RealmClient> waitings = new TreeMap<>();
	
	public static void onVersion(String arg1, RealmClient arg2) {
		if(arg1.equalsIgnoreCase((String)Main.getConfigValue("devemu.options.realm.vers"))) {
			arg2.setState(State.ACCOUNT);
		}else{
			Packet loc1 = new Packet();
			loc1.setIdentificator("Al");
			loc1.setFirstParam("Ev");
		}
	}
	
	public static void onAccount(String input, RealmClient client) {
        String[] data = input.split("\n");
        String username = data[0];
		String hashpass = data[1];

        Packet failure = Packet.factory()
                .identificator("Al")
                .firstParam("Ef")
                .create();

        Account account = Main.getSqlService().findAccountByName(username);

		if (account != null) {
			String password = Crypt.cryptAnkama(account.getPassword(), client.getSalt());

			if (hashpass.equals(password)) {
				client.setAcc(account);
				client.setState(State.SERVER);

				QueueSelector.addToQueue(client);
			} else {
				client.write(failure.toString());
			}
		} else {
			client.write(failure.toString());
		}
	}
	
	public static void onQueue(RealmClient arg1) {
        arg1.write(Packet.factory()
                .identificator("Af")
                .firstParam(arg1.getQueue())
                .params(QueueSelector.getTotAbo(), QueueSelector.getTotNonAbo())
                .param("0") // subscriber
                .param("1") // queue id
                .create()
                .toString());
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
		loc3.setFirstParam((String)Main.getConfigValue("devemu.options.realm.community"));
		
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
		
		List<Packet> loc7 = Arrays.asList(loc1, loc2, loc3, loc4, loc5);
		arg1.write(loc7);
	}
	
	public static void onServersList(RealmClient arg1) {
		Packet loc1 = new Packet();
		loc1.setIdentificator("Ax");
		loc1.setFirstParam("K" + AccountManager.getAboTime(arg1.getAcc()));

		List<String> loc2 = new ArrayList<>();
		Multiset<Integer> loc3 = TreeMultiset.create();
		for(Player loc4 : arg1.getAcc().getPlayers()) {
			loc3.add(loc4.getGameGuid());
		}
		for(Multiset.Entry<Integer> loc7 : loc3.entrySet()) {
			loc2.add(loc7.getElement() + "," + loc7.getCount());
		}
		loc1.setParam(loc2);
		arg1.write(loc1.toString());
	}
	
	
	public static void onConnection(Packet arg1, RealmClient arg2) {
		int loc1 = Integer.parseInt(arg1.getFirstParam());
		InterClient loc2 = ClientFactory.get(loc1);
		waitings.put(arg2.getAcc().getId(), arg2);
		
		ServerPacket loc3 = new ServerPacket();
		loc3.setId(InterId.ACC_WAITING.getId());
		loc3.getData().putInt(arg2.getAcc().getId());
		loc2.write(loc3.toBuff());
	}
	
	public static void onTransfer(int arg0,InterClient arg1) {
		RealmClient loc1 = waitings.get(arg0);
		waitings.remove(arg0);
		//TODO: UseCryptedIp
		Packet loc3 = new Packet();
		loc3.setIdentificator("AY");
		loc3.setFirstParam("K" + arg1.getIp() + ":" + arg1.getPort() + ";" + loc1.getAcc().getId());
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
