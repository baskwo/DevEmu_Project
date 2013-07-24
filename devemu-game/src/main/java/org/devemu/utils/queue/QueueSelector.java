package org.devemu.utils.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.server.client.GameClient;
import org.devemu.sql.manager.AccountManager;

import com.google.common.collect.Maps;

public class QueueSelector {
	private static Map<State,QueueSelector> selectors = Maps.newHashMap();
	
	private int nextAbo = 0;
	private int next = 0;
	private List<GameClient> queue = new ArrayList<GameClient>();
	private List<GameClient> queueAbo = new ArrayList<GameClient>();//Haha, fuck noSubscribe
	
	public static void init() {
		selectors.put(State.TRANSFERT, new QueueSelector());
		selectors.put(State.SELECTING, new QueueSelector());
	}
	
	public static QueueSelector getSelector(State state) {
		return selectors.get(state);
	}
	
	public synchronized void addToQueue(GameClient client) {
		if(AccountManager.getAboTime(client.getAcc()) > 0) {
			queueAbo.add(client);
			client.setQueue(nextAbo);
			nextAbo++;
		}
		else {
			queue.add(client);
			client.setQueue(next);
			next++;
		}
	}
	
	public synchronized void removeFromQueue(int index,boolean isSubscribe) {
		if(isSubscribe) {
			queueAbo.remove(index);
			nextAbo--;
			for(GameClient client : queueAbo) {
				if(client.getQueue() < index)
					continue;
				else if(client.getQueue() == index)
					client.setQueue(0);
				else {
					int pos = client.getQueue();
					client.setQueue(pos--);
				}
			}
		}else{
			queue.remove(index);
			next--;
			for(GameClient client : queue) {
				if(client.getQueue() < index)
					continue;
				else if(client.getQueue() == index)
					client.setQueue(0);
				else {
					int pos = client.getQueue();
					client.setQueue(pos--);
				}
			}
		}
	}
	
	public GameClient getFirst() {
		GameClient client = null;
		if(!queueAbo.isEmpty())
			client = queueAbo.get(0);
		else if(!queue.isEmpty())
			client = queue.get(0);
		return client;
	}
	
	public int getTotAbo() {
		return queueAbo.size();
	}

	public int getTotNonAbo() {
		return queue.size();
	}
}
