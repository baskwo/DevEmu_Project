package org.devemu.utils.queue;

import static com.google.common.base.Throwables.propagate;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.devemu.network.server.client.ClientManager;
import org.devemu.network.server.client.RealmClient;
import org.devemu.services.Startable;
import org.devemu.sql.entity.manager.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.typesafe.config.Config;

public class QueueManager implements Runnable,Startable {
	private static final Logger log = LoggerFactory.getLogger(QueueManager.class);
	private Config config;
	
	@Inject
	public QueueManager(Config config) {
		this.config = config;
	}
	
	@Override
	public void run() {
		RealmClient loc0 = QueueSelector.getFirst();
		if(loc0 != null) {
			boolean loc1 = AccountManager.getAboTime(loc0.getAcc()) > 0;
			QueueSelector.removeFromQueue(loc0.getQueue(),loc1);
			ClientManager.onStat(loc0);
		}
	}

	@Override
	public void start() {
		try {
			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS);
			log.debug("successfully started");
		}catch(Exception e) {
			log.error("start failure", e);
            throw propagate(e);
		}
		
	}

	@Override
	public void stop() {
		
	}
}
