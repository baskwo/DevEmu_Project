package org.devemu.network.inter;

import static com.google.common.base.Throwables.propagate;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.devemu.events.EventDispatcher;
import org.devemu.network.message.InterMessageFactory;
import org.devemu.program.Main;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.typesafe.config.Config;

public class InterServer implements Startable{
	private static final Logger log = LoggerFactory.getLogger(InterServer.class);
	
	private NioSocketAcceptor acceptor;
	private static InterServer instance;
	private final Config config;
	
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
	private InterServer(Config config,EventDispatcher dispatcher,InterMessageFactory factory) {
		this.config = config;
		acceptor = new NioSocketAcceptor();
		acceptor.setHandler(new InterHandler(dispatcher,factory));
	}
	
	@Override
	public void start() {
		try {
			acceptor.bind(new InetSocketAddress((String)Main.getConfigValue("devemu.service.inter.addr"), Integer.parseInt((String)Main.getConfigValue("devemu.service.inter.port"))));
			log.debug("successfully started");
		} catch (IOException e) {
			log.error("start failure", e);
            throw propagate(e);
		}
	}

	public NioSocketAcceptor getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(NioSocketAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	@Override
	public void stop() {
		acceptor.unbind();
        acceptor.dispose(true);
	}
}
