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
import org.devemu.network.protocol.filter.BanFilter;
import org.devemu.program.Main;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class GameServer implements Startable{
	private static final Logger log = LoggerFactory.getLogger(GameServer.class);
	
	private NioSocketAcceptor acceptor;
	private static GameServer instance;
	
	public static GameServer getInstance() {
		if(instance == null)
			instance = new GameServer();
		return instance;
	}

	@Inject
	private GameServer() {
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("throttle", new ConnectionThrottleFilter());
		acceptor.getFilterChain().addLast("ban", new BanFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("ISO-8859-1"),LineDelimiter.NUL, new LineDelimiter("\n\0"))));
		acceptor.setHandler(new GameHandler());
	}
	
	public void start() {
		try {
			acceptor.bind(new InetSocketAddress(Main.getConfigValue("devemu.service.game.addr"), Integer.parseInt(Main.getConfigValue("devemu.service.game.port"))));
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
