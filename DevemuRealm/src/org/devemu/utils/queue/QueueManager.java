package org.devemu.utils.queue;

import org.devemu.network.server.client.ClientManager;
import org.devemu.network.server.client.RealmClient;
import org.devemu.sql.entity.manager.AccountManager;

public class QueueManager implements Runnable {
	//private Thread thread;
	
	public QueueManager() {
		/*thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();*/
	}
	
	@Override
	public void run() {
		RealmClient loc0 = QueueSelector.getFirst();
		if(loc0 != null) {
			boolean loc1 = AccountManager.getAboTime(loc0.getAcc()) > 0;
			QueueSelector.removeFromQueue(loc0.getQueue(),loc1);
			ClientManager.onStat(loc0);
		}
		/*while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			RealmClient loc0 = QueueSelector.getFirst();
			if(loc0 != null) {
				boolean loc1 = AccountManager.getAboTime(loc0.getAcc()) > 0;
				QueueSelector.removeFromQueue(loc0.getQueue(),loc1);
				ClientManager.onStat(loc0);
			}
		}*/
	}
}
