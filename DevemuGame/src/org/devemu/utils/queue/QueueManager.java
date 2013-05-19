package org.devemu.utils.queue;

import org.devemu.network.server.client.ClientManager;
import org.devemu.network.server.client.GameClient;
import org.devemu.program.Main.Queue;
import org.devemu.sql.manager.AccountManager;

public class QueueManager implements Runnable {
	//private Thread thread;
	private QueueSelector selector = new QueueSelector();
	private Queue type;

	public QueueManager(Queue arg0) {
		type = arg0;
		/*thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();*/
	}
	
	@Override
	public void run() {
		GameClient loc0 = selector.getFirst();
		if(loc0 != null) {
			boolean loc1 = AccountManager.getAboTime(loc0.getAcc()) > 0;
			selector.removeFromQueue(loc0.getQueue(),loc1);
			if(type == Queue.TRANSFERT) {
				ClientManager.onCharacterList(loc0);
			}else{
				ClientManager.onSelected(loc0);
			}
		}
		/*while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			GameClient loc0 = selector.getFirst();
			if(loc0 != null) {
				boolean loc1 = AccountManager.getAboTime(loc0.getAcc()) > 0;
				selector.removeFromQueue(loc0.getQueue(),loc1);
				if(type == Queue.TRANSFERT) {
					ClientManager.onCharacterList(loc0);
				}else{
					ClientManager.onSelected(loc0);
				}
			}
		}*/
	}
	
	public QueueSelector getSelector() {
		return selector;
	}
}
