package org.devemu.utils.queue;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.devemu.queue.QueueListener;
import org.devemu.services.Startable;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class GameQueueThread implements Startable,Runnable {
	List<QueueListener> listeners = Lists.newArrayList();
	ScheduledExecutorService executor;
	
	@Inject
	public GameQueueThread(@Named("selection") QueueListener sListener,@Named("transfert") QueueListener tListener) {
		listeners.add(sListener);
		listeners.add(tListener);
		executor = Executors.newSingleThreadScheduledExecutor();
	}

	@Override
	public void start() {
		executor.scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS);
	}

	@Override
	public void stop() {
		executor.shutdown();
	}

	@Override
	public void run() {
		for(QueueListener listener : listeners) {
			listener.invoke(listener.getFirst());
		}
	}

}
