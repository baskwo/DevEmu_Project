package org.devemu.utils.queue;

import java.util.ArrayList;
import java.util.List;

import org.devemu.network.server.client.RealmClient;
import org.devemu.sql.entity.manager.AccountManager;

public class QueueSelector {
	private static int nextAbo = 0;
	private static int next = 0;
	private static List<RealmClient> queue = new ArrayList<>();
	private static List<RealmClient> queueAbo = new ArrayList<>();

	public static List<RealmClient> getQueue() {
		return queue;
	}
	
	public static synchronized void addToQueue(RealmClient arg0) {
		if(AccountManager.getAboTime(arg0.getAcc()) > 0) {
			queueAbo.add(arg0);
			arg0.setQueue(nextAbo);
			nextAbo++;
		}
		else {
			queue.add(arg0);
			arg0.setQueue(next);
			next++;
		}
		arg0.setQueueCur(next+nextAbo);
	}
	
	public static synchronized void removeFromQueue(int arg0,boolean arg1) {
		if(arg1) {
			queueAbo.remove(arg0);
			nextAbo--;
			for(RealmClient loc1 : queueAbo) {
				if(loc1.getQueue() < arg0)
					continue;
				else if(loc1.getQueue() == arg0)
					loc1.setQueue(0);
				else {
					int loc2 = loc1.getQueue();
					loc1.setQueue(loc2--);
				}
			}
		}else{
			queue.remove(arg0);
			next--;
			for(RealmClient loc1 : queue) {
				if(loc1.getQueue() < arg0)
					continue;
				else if(loc1.getQueue() == arg0)
					loc1.setQueue(0);
				else {
					int loc2 = loc1.getQueue();
					loc1.setQueue(loc2--);
				}
			}
		}
	}
	
	public static RealmClient getFirst() {
		RealmClient loc0 = null;
		if(!queueAbo.isEmpty())
			loc0 = queueAbo.get(0);
		else if(!queue.isEmpty())
			loc0 = queue.get(0);
		return loc0;
	}
	
	public static int getTotAbo() {
		return queueAbo.size();
	}

	public static int getTotNonAbo() {
		return queue.size();
	}
}
