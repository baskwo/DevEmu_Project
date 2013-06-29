package org.devemu.utils.queue;

import static com.google.common.base.Throwables.propagate;

import java.util.concurrent.TimeUnit;

import org.devemu.events.EventDispatcher;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.server.client.GameClient;
import org.devemu.sql.manager.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Inject;

public class QueueManager  extends AbstractScheduledService{
	private static final Logger log = LoggerFactory.getLogger(QueueManager.class);
	
	private EventDispatcher dispatcher;
	private MessageFactory factory;
	
	@Inject
	public QueueManager(EventDispatcher dispatcher,MessageFactory factory) {
		this.dispatcher = dispatcher;
		this.factory = factory;
	}
	
	@Override
	protected void runOneIteration() throws Exception {
		try{
			GameClient loc0 = QueueSelector.getFirst();
			if(loc0 != null) {
				boolean loc1 = AccountManager.getAboTime(loc0.getAcc()) > 0;
				QueueSelector.removeFromQueue(loc0.getQueue(),loc1);
				//ClientManager.onCharacterList(loc0);
			}
		}catch(Exception e) {throw propagate(e);}
	}
	
	@Override
	public void startUp() {
	}

	@Override
	public void shutDown() {
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
