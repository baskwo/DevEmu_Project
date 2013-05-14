package org.devemu.network.server;

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
import org.devemu.utils.config.ConfigEnum;

public class RealmServer {
	private NioSocketAcceptor acceptor;
	private static RealmServer instance;
	
	public static RealmServer getInstance() {
		if(instance == null)
			instance = new RealmServer();
		return instance;
	}

	private RealmServer() {
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("throttle", new ConnectionThrottleFilter());
		acceptor.getFilterChain().addLast("ban", new BanFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("ISO-8859-1"),LineDelimiter.NUL, new LineDelimiter("\n\0"))));
		acceptor.setHandler(new RealmHandler());
	}
	
	public void start() {
		try {
			acceptor.bind(new InetSocketAddress((String)Main.getConfigValue(ConfigEnum.REALM_IP), Integer.parseInt((String)Main.getConfigValue(ConfigEnum.REALM_PORT))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public NioSocketAcceptor getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(NioSocketAcceptor acceptor) {
		this.acceptor = acceptor;
	}

}
