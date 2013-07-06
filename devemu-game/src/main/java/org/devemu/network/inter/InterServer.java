package org.devemu.network.inter;

import static com.google.common.base.Throwables.propagate;

import java.net.InetSocketAddress;

import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.devemu.events.EventDispatcher;
import org.devemu.network.message.InterMessageFactory;
import org.devemu.program.Main;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class InterServer implements Startable {
	private static final Logger log = LoggerFactory.getLogger(InterServer.class);
	
	private NioSocketConnector connector;
	private static InterServer instance;
	
	public static InterServer getInstance() {
		if(instance == null)
			try {
				instance = InterServer.class.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw propagate(e);
			}
		return instance;
	}
	
	@Inject
	private InterServer(EventDispatcher dispatcher,InterMessageFactory factory) {
		connector = new NioSocketConnector();
		connector.setHandler(new InterHandler(dispatcher,factory));
	}
	
	@Override
	public void start() {
		try {
			instance = this;
			connector.connect(new InetSocketAddress(Main.getConfigValue("devemu.service.inter.addr"), Integer.parseInt(Main.getConfigValue("devemu.service.inter.port"))));
			log.debug("successfully started");
		} catch (Exception e) {
            log.error("start failure", e);
            throw propagate(e);
		}
	}

	public NioSocketConnector getConnector() {
		return connector;
	}

	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}

	@Override
	public void stop() {
		connector.dispose();
	}
}
