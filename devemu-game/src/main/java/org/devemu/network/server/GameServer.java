package org.devemu.network.server;

import static com.google.common.base.Throwables.propagate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.firewall.ConnectionThrottleFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.devemu.events.EventDispatcher;
import org.devemu.network.message.MessageFactory;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.typesafe.config.Config;

public class GameServer implements Startable{
	private static final Logger log = LoggerFactory.getLogger(GameServer.class);
	
	private NioSocketAcceptor acceptor;
	private static GameServer instance;
	private Config config;
	
	public static GameServer getInstance() {
		if(instance == null)
			try {
				instance = GameServer.class.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw propagate(e);
			}
		return instance;
	}

	@Inject
	private GameServer(Config config,EventDispatcher dispatcher,MessageFactory factory) {
		this.config = config;
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("throttle", new ConnectionThrottleFilter());
		//TODO: BanFilter acceptor.getFilterChain().addLast("ban", new BanFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("ISO-8859-1"),LineDelimiter.NUL, new LineDelimiter("\n\0"))));
		acceptor.setHandler(new GameHandler(dispatcher,factory));
	}
	
	public void start() {
		try {
			instance = this;
			acceptor.bind(new InetSocketAddress(config.getString("devemu.service.game.addr"), config.getInt("devemu.service.game.port")));
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
