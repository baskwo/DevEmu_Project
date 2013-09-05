package org.devemu.network.event.handler;

import org.devemu.events.Subscribe;
import org.devemu.network.client.BaseClient.State;
import org.devemu.network.event.event.login.ClientLoginEvent;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.network.server.client.RealmClient;
import org.devemu.network.server.message.login.LoginAccountMessage;
import org.devemu.network.server.message.login.LoginVersionMessage;
import org.devemu.network.server.message.login.agreed.AccountInfoMessage;
import org.devemu.network.server.message.queue.QueueMessage;
import org.devemu.network.server.message.server.ServerListMessage;
import org.devemu.network.server.message.server.ServerPersoListMessage;
import org.devemu.program.Main;
import org.devemu.queue.QueueListener;
import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.Player;
import org.devemu.utils.Crypt;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import static com.google.common.base.Throwables.propagate;

public class LoginEventHandler {
	@Inject @Named("login") QueueListener listener;
	@Inject MessageFactory factory;
	
	@Subscribe(ClientLoginEvent.class)
	public void onVersion(RealmClient client, LoginVersionMessage message) {
		if(message.version.equals(Main.getConfigValue("devemu.options.realm.vers"))){
			client.setState(State.ACCOUNT);
		}
		else {
			//TODO: Error
		}
	}
	
	@Subscribe(ClientLoginEvent.class)
	public void onAccount(RealmClient client, LoginAccountMessage message) {
		Account account = null;
		if(message.username != "") {
			account = client.getAccHelper().findByName(message.username);
		}
		
		if (account != null) {
			String password = Crypt.cryptAnkama(account.getPassword(), client.getSalt());

			if (message.passHash.equals(password)) {
				client.setAcc(account);
				client.setState(State.SERVER);
				
				listener.add(client);
			} else {
				//TODO: Error
			}
		} else {
			//TODO: Error
		}
	}
	
	@Subscribe(ClientLoginEvent.class)
	public void onQueue(RealmClient client, QueueMessage message) {
		message.currentPos = client.getQueue();
		message.subscriber = client.getAccHelper().getAboTime(client.getAcc()) > 0 ? true : false;
		message.aboSize = listener.getQueueAbo().size();
		message.nonAboSize = listener.getQueue().size();
		message.serialize();
		client.write(message.output);
	}
	
	@Subscribe(ClientLoginEvent.class)
	public void onInfo(RealmClient client, AccountInfoMessage message) {
		message.isAdmin = client.getAcc().isAdmin();
		message.pseudo = client.getAcc().getPseudo();
		message.question = client.getAcc().getQuestion();
		
		message.serialize();
		client.write(message.output);
		
		try {
			ServerListMessage o = (ServerListMessage) factory.getMessage("AH",State.SERVER);
			o.serialize();
			client.write(o.output);
		} catch (MessageNotFoundException e) {
			throw propagate(e);
		}
	}
	
	@Subscribe(ClientLoginEvent.class)
	public void onServerList(RealmClient client, ServerPersoListMessage message) {
		Multiset<Integer> set = TreeMultiset.create();
		for(Player p : client.getAcc().getPlayers()) {
			set.add(p.getGameGuid());
		}
		message.list = set;
		message.aboTime = client.getAccHelper().getAboTime(client.getAcc());
		message.serialize();
		client.write(message.output);
	}
}
