package org.devemu.network.inter;

import java.net.InetSocketAddress;

import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.devemu.program.Main;
import org.devemu.utils.config.ConfigEnum;

public class InterServer {
	private NioSocketConnector connector;
	private static InterServer instance;
	
	public static InterServer getInstance() {
		if(instance == null)
			instance = new InterServer();
		return instance;
	}
	
	private InterServer() {
		connector = new NioSocketConnector();
		connector.setHandler(new InterHandler());
	}
	
	public void start() {
		connector.connect(new InetSocketAddress((String)Main.getConfigValue(ConfigEnum.INTER_IP), Integer.parseInt((String)Main.getConfigValue(ConfigEnum.INTER_PORT))));
	}

	public NioSocketConnector getConnector() {
		return connector;
	}

	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}
}
