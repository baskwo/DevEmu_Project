<<<<<<< HEAD:DevemuGame/src/org/devemu/utils/queue/QueueSelector.java
package org.devemu.utils.queue;

import java.util.ArrayList;
import java.util.List;

import org.devemu.network.server.client.GameClient;
import org.devemu.sql.manager.AccountManager;

public class QueueSelector {
	private int nextAbo = 0;
	private int next = 0;
	private List<GameClient> queue = new ArrayList<>();
	private List<GameClient> queueAbo = new ArrayList<>();//Haha, fuck noSubscribe
	
	public synchronized void addToQueue(GameClient arg0) {
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
	
	public synchronized void removeFromQueue(int arg0,boolean arg1) {
		if(arg1) {
			queueAbo.remove(arg0);
			nextAbo--;
			for(GameClient loc1 : queueAbo) {
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
			for(GameClient loc1 : queue) {
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
	
	public GameClient getFirst() {
		GameClient loc0 = null;
		if(!queueAbo.isEmpty())
			loc0 = queueAbo.get(0);
		else if(!queue.isEmpty())
			loc0 = queue.get(0);
		return loc0;
	}
	
	public int getTotAbo() {
		return queueAbo.size();
	}

	public int getTotNonAbo() {
		return queue.size();
	}
}
=======
package org.devemu.utils.queue;

import java.util.ArrayList;
import java.util.List;

import org.devemu.network.server.client.GameClient;
import org.devemu.sql.manager.AccountManager;

public class QueueSelector {
	private int nextAbo = 0;
	private int next = 0;
	private List<GameClient> queue = new ArrayList<GameClient>();
	private List<GameClient> queueAbo = new ArrayList<GameClient>();//Haha, fuck noSubscribe
	
	public synchronized void addToQueue(GameClient arg0) {
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
	
	public synchronized void removeFromQueue(int arg0,boolean arg1) {
		if(arg1) {
			queueAbo.remove(arg0);
			nextAbo--;
			for(GameClient loc1 : queueAbo) {
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
			for(GameClient loc1 : queue) {
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
	
	public GameClient getFirst() {
		GameClient loc0 = null;
		if(!queueAbo.isEmpty())
			loc0 = queueAbo.get(0);
		else if(!queue.isEmpty())
			loc0 = queue.get(0);
		return loc0;
	}
	
	public int getTotAbo() {
		return queueAbo.size();
	}

	public int getTotNonAbo() {
		return queue.size();
	}
}
>>>>>>> 800500b6eda7a3b433414a80b9c5ef278d93b04e:devemu-game/src/main/java/org/devemu/utils/queue/QueueSelector.java
