package org.devemu.network.server;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.firewall.ConnectionThrottleFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.devemu.network.protocol.filter.BanFilter;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import static com.google.common.base.Throwables.propagate;

public class RealmServer implements Startable {
    private static final Logger log = LoggerFactory.getLogger(RealmServer.class);

	private static RealmServer instance;
	
	public static RealmServer getInstance() {
		if(instance == null)
			instance = new RealmServer(null);
		return instance;
	}


    private NioSocketAcceptor acceptor;
    private final Config config;

    @Inject
	public RealmServer(Config config) {
        this.config = config;

        acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("throttle", new ConnectionThrottleFilter());
		acceptor.getFilterChain().addLast("ban", new BanFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"),
                LineDelimiter.NUL,
                new LineDelimiter("\n\0")
        )));
		acceptor.setHandler(new RealmHandler());
	}

    @Override
	public void start() {
		try {
			acceptor.bind(new InetSocketAddress(config.getString("devemu.service.laddr"), config.getInt("devemu.service.port")));
            log.debug("successfully started");
		} catch (IOException e) {
            log.error("start failure", e);
            throw propagate(e);
		}
	}

    @Override
    public void stop() {
        acceptor.unbind();
        acceptor.dispose(true);
    }

	public NioSocketAcceptor getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(NioSocketAcceptor acceptor) {
		this.acceptor = acceptor;
	}

}
