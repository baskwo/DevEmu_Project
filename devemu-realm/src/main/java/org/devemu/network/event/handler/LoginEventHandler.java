package org.devemu.network.event.handler;

import org.devemu.events.Subscribe;
import org.devemu.network.client.BaseClient.State;
import org.devemu.network.event.event.login.ClientLoginEvent;
import org.devemu.network.server.client.RealmClient;
import org.devemu.network.server.message.login.LoginAccountMessage;
import org.devemu.network.server.message.login.LoginVersionMessage;
import org.devemu.network.server.message.login.agreed.AccountInfoMessage;
import org.devemu.network.server.message.queue.QueueMessage;
import org.devemu.network.server.message.server.ServerListMessage;
import org.devemu.network.server.message.server.ServerPersoListMessage;
import org.devemu.program.Main;
import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.Player;
import org.devemu.sql.entity.manager.AccountManager;
import org.devemu.utils.Crypt;
import org.devemu.utils.queue.QueueSelector;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

public class LoginEventHandler {
	
	@Subscribe(ClientLoginEvent.class)
	public void onVersion(RealmClient client, LoginVersionMessage message) {
		if(message.version.equals(Main.getConfigValue("devemu.options.realm.vers")))
			client.setState(State.ACCOUNT);
		else {
			//TODO: Error
		}
	}
	
	@Subscribe(ClientLoginEvent.class)
	public void onAccount(RealmClient client, LoginAccountMessage message) {
		Account account = null;
		
		if(message.username != "")
			account = Main.getSqlService().findAccountByName(message.username);
		
		if (account != null) {
			String password = Crypt.cryptAnkama(account.getPassword(), client.getSalt());

			if (message.passHash.equals(password)) {
				client.setAcc(account);
				client.setState(State.SERVER);

				QueueSelector.addToQueue(client);
			} else {
				//TODO: Error
			}
		} else {
			//TODO: Error
		}
	}
	
	@Subscribe(ClientLoginEvent.class)
	public void onQueue(RealmClient client, QueueMessage message) {
		message.currentPos = client.getQueueCur();
		message.subscriber = AccountManager.getAboTime(client.getAcc()) > 0 ? true : false;
		message.serialize();
		client.write(message.output);
	}
	
	@Subscribe(ClientLoginEvent.class)
	public void onStat(RealmClient client, AccountInfoMessage message) {
		message.isAdmin = client.getAcc().isAdmin();
		message.pseudo = client.getAcc().getPseudo();
		message.question = client.getAcc().getQuestion();
		
		message.serialize();
		client.write(message.output);
		
		ServerListMessage o = new ServerListMessage();
		o.serialize();
		client.write(o.output);
	}
	
	@Subscribe(ClientLoginEvent.class)
	public void onServerList(RealmClient client, ServerPersoListMessage message) {
		Multiset<Integer> loc3 = TreeMultiset.create();
		for(Player loc4 : client.getAcc().getPlayers()) {
			loc3.add(loc4.getGameGuid());
		}
		message.list = loc3;
		message.aboTime = AccountManager.getAboTime(client.getAcc());
		message.serialize();
		client.write(message.output);
	}
}
