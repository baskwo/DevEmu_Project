package org.devemu.utils.queue;

import java.util.ArrayList;
import java.util.List;

import org.devemu.network.server.client.RealmClient;
import org.devemu.sql.entity.manager.AccountManager;

public class QueueSelector {
	private static int nextAbo = 0;
	private static int next = 0;
	private static List<RealmClient> queue = new ArrayList<RealmClient>();
	private static List<RealmClient> queueAbo = new ArrayList<RealmClient>();

	public static List<RealmClient> getQueue() {
		return queue;
	}
	
	public static synchronized void addToQueue(RealmClient client) {
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
	
	public static synchronized void removeFromQueue(int index,boolean isSubscribe) {
		if(isSubscribe) {
			queueAbo.remove(index);
			nextAbo--;
			for(RealmClient client : queueAbo) {
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
			for(RealmClient client : queue) {
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
	
	public static RealmClient getFirst() {
		RealmClient client = null;
		if(!queueAbo.isEmpty())
			client = queueAbo.get(0);
		else if(!queue.isEmpty())
			client = queue.get(0);
		return client;
	}
	
	public static int getTotAbo() {
		return queueAbo.size();
	}

	public static int getTotNonAbo() {
		return queue.size();
	}
}
