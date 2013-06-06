package org.devemu.network.inter;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.devemu.program.Main;
import org.devemu.utils.config.ConfigEnum;

public class InterServer {
	private NioSocketAcceptor acceptor;
	private static InterServer instance;
	
	public static InterServer getInstance() {
		if(instance == null)
			instance = new InterServer();
		return instance;
	}
	
	private InterServer() {
		acceptor = new NioSocketAcceptor();
		acceptor.setHandler(new InterHandler());
	}
	
	public void start() {
		try {
			acceptor.bind(new InetSocketAddress((String)Main.getConfigValue(ConfigEnum.INTER_IP), Integer.parseInt((String)Main.getConfigValue(ConfigEnum.INTER_PORT))));
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
