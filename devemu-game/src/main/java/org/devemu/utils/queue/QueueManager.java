package org.devemu.utils.queue;

import java.util.concurrent.TimeUnit;

import org.devemu.events.EventDispatcher;
import org.devemu.network.client.BaseClient;
import org.devemu.network.event.event.game.GameClientReuseEvent;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.server.client.GameClient;
import org.devemu.sql.manager.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Inject;

public class QueueManager  extends AbstractScheduledService{
	private static final Logger log = LoggerFactory.getLogger(QueueManager.class);
	
	@Inject
	private EventDispatcher dispatcher;
	@Inject
	private MessageFactory factory;
	
	@Override
	protected void runOneIteration() throws Exception {
		try{
			GameClient client = QueueSelector.getSelector(BaseClient.State.TRANSFERT).getFirst();
			if(client != null) {
				boolean isSubscribe = AccountManager.getAboTime(client.getAcc()) > 0;
				QueueSelector.getSelector(BaseClient.State.TRANSFERT).removeFromQueue(client.getQueue(),isSubscribe);
				dispatcher.dispatch(new GameClientReuseEvent(client,factory.getMessage("AL")));
			}
			client = null;
			client = QueueSelector.getSelector(BaseClient.State.SELECTING).getFirst();
			if(client != null) {
				boolean isSubscribe = AccountManager.getAboTime(client.getAcc()) > 0;
				QueueSelector.getSelector(BaseClient.State.SELECTING).removeFromQueue(client.getQueue(),isSubscribe);
				dispatcher.dispatch(new GameClientReuseEvent(client,factory.getMessage("AS")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void startUp() {
		QueueSelector.init();
		log.debug("QueueManager starUp");
	}

	@Override
	public void shutDown() {
		log.debug("QueueManager shutdown");
	}
	
	@Override
	protected Scheduler scheduler() {
		return new CustomScheduler() {
            @Override
            protected Schedule getNextSchedule() throws Exception {
                long a = 1000;
                return new Schedule(a, TimeUnit.MILLISECONDS);
            }
        };
	}

}
