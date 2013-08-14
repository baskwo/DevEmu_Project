package org.devemu.network.server;

import static com.google.common.base.Throwables.propagate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.firewall.ConnectionThrottleFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.devemu.events.EventDispatcher;
import org.devemu.network.event.event.game.GameClientEvent;
import org.devemu.network.message.Message;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.network.server.client.GameClient;
import org.devemu.network.server.message.connect.ClientConnectMessage;
import org.devemu.program.Main;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.typesafe.config.Config;

public class GameServer extends IoHandlerAdapter implements Startable{
	private static final Logger log = LoggerFactory.getLogger(GameServer.class);
	
	private NioSocketAcceptor acceptor;
	private Config config;
	private EventDispatcher dispatcher;
	private MessageFactory factory;
	private Provider<GameClient> clientProvider;

	@Inject
	private GameServer(Config config,EventDispatcher dispatcher,MessageFactory factory,Provider<GameClient> provider) {
		this.clientProvider = provider;
		this.dispatcher = dispatcher;
		this.factory = factory;
		this.config = config;
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("throttle", new ConnectionThrottleFilter());
		//TODO: BanFilter acceptor.getFilterChain().addLast("ban", new BanFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("ISO-8859-1"),LineDelimiter.NUL, new LineDelimiter("\n\0"))));
		acceptor.setHandler(this);
	}
	
	public void start() {
		try {
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
	
	public void sessionCreated(IoSession session) throws Exception{
		GameClient client = clientProvider.get();
		client.setSession(session);
		session.setAttribute("client", client);
		
		ClientConnectMessage o = new ClientConnectMessage();
		o.serialize();
		session.write(o.output);
	}
	
	public void sessionClosed(IoSession session) throws Exception{
		//TODO:Save
		session.close(true);
	}
	
	@Override
	public void exceptionCaught(IoSession session,Throwable cause) throws Exception{
		if(cause.getCause() instanceof MessageNotFoundException) {
			log.error("Message not found : {}", cause.getMessage());
		}else if(Main.getConfigValue("devemu.options.other.debug").equals("true")){
			cause.printStackTrace();
		}
	}
	
	public void messageReceived(IoSession session, Object message) throws Exception{
		String in = message.toString();
		if(session.getAttribute("client") instanceof GameClient) {
			GameClient client = (GameClient)session.getAttribute("client");
			log.debug("Receiving : {}  from : {}",in,session.getRemoteAddress().toString());
			Message o = factory.getMessage(in.substring(0, 2));
			if(o == null) {
				log.debug("Message not found {}",in.substring(0,2));
				return;
			}
			o.setInput(in);
			o.deserialize();
			dispatcher.dispatch(new GameClientEvent(client,o));
		}
	}
	
	public void messageSent(IoSession session, Object message) throws Exception{
		String out = message.toString();
		log.debug("Sending : {}  from : {}",out,session.getRemoteAddress().toString());
	}
}
