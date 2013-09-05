package org.devemu.utils.queue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.devemu.queue.QueueListener;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LoginQueueThread implements Startable,Runnable {
	private static final Logger log = LoggerFactory.getLogger(LoginQueueThread.class);
	QueueListener listener;
	ScheduledExecutorService executor;
	
	@Inject
	public LoginQueueThread(@Named("login") QueueListener listener) {
		this.listener = listener;
		executor = Executors.newSingleThreadScheduledExecutor();
	}

	@Override
	public void start() {
		executor.scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS);
		log.debug("successfully started");
	}

	@Override
	public void stop() {
		executor.shutdown();
	}

	@Override
	public void run() {
		if(!listener.isEmpty()) {
			listener.invoke(listener.getFirst());
		}
	}

}
