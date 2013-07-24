package org.devemu.utils.queue;

import static com.google.common.base.Throwables.propagate;

import java.util.concurrent.TimeUnit;

import org.devemu.events.EventDispatcher;
import org.devemu.network.event.event.login.ClientLoginEvent;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.server.client.RealmClient;
import org.devemu.sql.entity.manager.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Inject;

public class QueueManager extends AbstractScheduledService{
	private static final Logger log = LoggerFactory.getLogger(QueueManager.class);
	
	@Inject
	private EventDispatcher dispatcher;
	@Inject
	private MessageFactory factory;
	
	@Override
	public void startUp() {
		log.debug("QueueManager starUp");
	}

	@Override
	public void shutDown() {
		log.debug("QueueManager shutdown");
	}

	@Override
	protected void runOneIteration() throws Exception {
		try {
			RealmClient client = QueueSelector.getFirst();
			if(client != null) {
				boolean isSubscribe = AccountManager.getAboTime(client.getAcc()) > 0;
				QueueSelector.removeFromQueue(client.getQueue(),isSubscribe);
				dispatcher.dispatch(new ClientLoginEvent(client,factory.getMessage("Al", client.getState())));
			}
		}catch(Exception e) {
			throw propagate(e);
		}
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
