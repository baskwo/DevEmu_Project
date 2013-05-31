package org.devemu.network.server.client;

import java.util.List;

import org.devemu.network.inter.client.InterManager;
import org.devemu.network.protocol.Packet;
import org.devemu.program.Main;
import org.devemu.program.Main.Queue;
import org.devemu.sql.entity.Player;
import org.devemu.sql.manager.AccountManager;
import org.devemu.sql.manager.PlayerManager;

public class ClientManager {
	public static void onTransfert(GameClient arg0, Packet arg1) {
		int loc1 = Integer.parseInt(arg1.getFirstParam());
		if(InterManager.waitings.contains(loc1)) {
			InterManager.waitings.remove((Object)loc1);
			arg0.setAcc(AccountManager.findById(loc1));
			onTransferAnswer(arg0,true);
		}else{
			onTransferAnswer(arg0,false);
		}
	}
	
	public static void onTransferAnswer(GameClient arg0, boolean arg1) {
		Packet loc1 = new Packet();
		loc1.setIdentificator("AT");
		if(arg1)
			loc1.setFirstParam("K0");
		else
			loc1.setFirstParam("E");
		arg0.write(loc1.toString());
	}
	
	public static void onRegionalVersion(GameClient arg0) {
		Packet loc2 = new Packet();
		loc2.setIdentificator("AV");
		loc2.setFirstParam("0");
		arg0.write(loc2.toString());
	}
	
	public static void onQueue(GameClient arg1,Queue arg2) {
		Packet loc2 = new Packet();
		loc2.setIdentificator("Af");
		loc2.setFirstParam(""+arg1.getQueue());
		List<String> loc3 = loc2.getParam();
		loc3.add(""+Main.getQueue(arg2).getSelector().getTotAbo());
		loc3.add(""+Main.getQueue(arg2).getSelector().getTotNonAbo());
		loc3.add("0"); //Subscriber
		loc3.add("" + Main.getGuid());
		loc2.setParam(loc3);
		arg1.write(loc2.toString());
	}
	
	public static void onWantingList(GameClient arg1) {
		Main.getQueue(Queue.TRANSFERT).getSelector().addToQueue(arg1);
	}
	
	public static void onIdentification(GameClient arg0, Packet arg1) {
		arg0.setIdentification(arg1.getFirstParam());
	}
	
	public static void onSelecting(GameClient arg0,Packet arg1) {
		arg0.setPlayerIdTemp(Integer.parseInt(arg1.getFirstParam()));
		Main.getQueue(Queue.SELECTING).getSelector().addToQueue(arg0);
	}
	
	public static void onSelected(GameClient arg0) {
		arg0.setPlayer(PlayerManager.getById(arg0.getPlayerIdTemp()));
		onJoin(arg0);
	}
	
	public static void onJoin(GameClient arg0) {
		//TODO: Send Re (Drago stats)
				//TODO: Send Rx (Drago xp)
		
		Packet loc3 = new Packet();
		loc3.setIdentificator("AS");
		loc3.setFirstParam("K");
		loc3.setParam(PlayerManager.toASK(arg0.getPlayer()));
		arg0.write(loc3.toString());
		
				//TODO: Send OS (Object Set bonus)
				//TODO: Send JS (All job stats)
				//TODO: Send JX (Job level,xp)
				//TODO: Send JO (Job option)
				//TODO: Send OT (Item tool)
				//TODO: Send Align 
				//TODO: Send AddCanal
				//TODO: Send gS if guildMember
				//TODO: Send ZoneAlign
				//TODO: Send SpellList
				//TODO: Send EmoteList
				//TODO: Send Restriction
				//TODO: Send Ow (Pod use,max)
				//TODO: Send SeeFriendConnection
				//TODO: Send online to friend
				//TODO: Send welcome message
				//TODO: Send ILS
	}
	
	public static void onCharacterList(GameClient arg1) {
		Packet loc1 = new Packet();
		loc1.setIdentificator("AL");
		loc1.setFirstParam("K" + AccountManager.getAboTime(arg1.getAcc()));
		List<String> loc2 = loc1.getParam();
		loc2.add("" + arg1.getAcc().getPlayers().size());
		for(Integer loc3 : arg1.getAcc().getPlayers()) {
			//loc2.add(PlayerManager.toALK(loc3));
			//TODO: Find player and to ALK
		}
		loc1.setParam(loc2);
		arg1.write(loc1.toString());
		arg1.setState(Queue.SELECTING);
	}
	
	public static void onCharacterCreation(GameClient arg1, Packet arg2) {
		Player loc1 = new Player();
		//TODO: name already exist, forbbiden character, etc
		loc1.setGuid(PlayerManager.getNextId());
		loc1.setName(arg2.getFirstParam());
		
		List<String> loc2 = arg2.getParam();
		loc1.setClasse(Integer.parseInt(loc2.get(0)));
		loc1.setSexe(loc2.get(1).equalsIgnoreCase("1") ? true : false);
		loc1.setColors(new int[]{Integer.parseInt(loc2.get(2)),Integer.parseInt(loc2.get(3)),Integer.parseInt(loc2.get(4))});
		loc1.setGameGuid(Main.getGuid());
		
		PlayerManager.generateBaseStats(loc1);
		
		Packet loc3 = new Packet();
		loc3.setIdentificator("AA");
		try {
			PlayerManager.createOnDb(loc1,arg1);
		} catch(Exception e) {
			loc3.setFirstParam("E");
			arg1.write(loc3.toString());
			e.printStackTrace();
			return;
		}
		loc3.setFirstParam("K");
		arg1.write(loc3.toString());
		onCharacterList(arg1);
	}
	
	public static void onCharacterDeletion(GameClient arg1, Packet arg2) {
		int loc1 = Integer.parseInt(arg2.getFirstParam());
		Player loc2 = PlayerManager.getById(loc1);
		String loc3 = "";//Answer
		//TODO: Answser Handling
		if(!arg2.getParam().isEmpty())
			loc3 =  arg2.getParam().get(0);
		try {
			PlayerManager.deleteOnDb(loc2, arg1);
		}catch(Exception e) {
			e.printStackTrace();
			Packet loc4 = new Packet();
			loc4.setIdentificator("AD");
			loc4.setFirstParam("E");
			arg1.write(loc4.toString());
			return;
		}
		onCharacterList(arg1);
	}
	
	public static void onGameCreation(GameClient arg1, Packet arg2) {
		//int loc1 = Integer.parseInt(arg2.getFirstParam());//gameType
		
		Packet loc2 = new Packet();
		loc2.setIdentificator("GC");
		loc2.setFirstParam("K");
		loc2.getParam().add(arg2.getFirstParam());
		loc2.getParam().add(arg1.getPlayer().getName());
		arg1.write(loc2.toString());
		
		Packet loc3 = new Packet();
		loc3.setIdentificator("As");
		loc3.setFirstParam(PlayerManager.getXpToString(arg1.getPlayer()));
		loc3.setParam(PlayerManager.getAsData(arg1.getPlayer()));
		arg1.write(loc3.toString());
	}
}
