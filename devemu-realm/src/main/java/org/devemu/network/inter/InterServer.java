package org.devemu.network.inter;

import static com.google.common.base.Throwables.propagate;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.devemu.events.EventDispatcher;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterMessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.program.Main;
import org.devemu.services.Startable;
import org.devemu.utils.enums.ServerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.typesafe.config.Config;

public class InterServer extends IoHandlerAdapter implements Startable{
	private static final Logger log = LoggerFactory.getLogger(InterServer.class);
	
	private NioSocketAcceptor acceptor;
	private static InterServer instance;
	private final Config config;
	private EventDispatcher dispatcher;
	private InterMessageFactory factory;
	
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
		this.dispatcher = dispatcher;
		this.factory = factory;
		this.config = config;
		acceptor = new NioSocketAcceptor();
		acceptor.setHandler(this);
	}
	
	@Override
	public void start() {
		try {
			instance = this;
			acceptor.bind(new InetSocketAddress(config.getString("devemu.service.inter.addr"), config.getInt("devemu.service.inter.port")));
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
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception{
		if(message instanceof IoBuffer) {
			IoBuffer o = (IoBuffer)message;
			int id = o.get();
			int serv = o.get();
			log.debug("Receiving : {} from : {}",id ,session.getRemoteAddress());
			InterClient server = ClientFactory.get(serv);
			if(server.getSession() == null)
				server.setSession(session);
			session.setAttribute(this, server);
			InterMessage packet = factory.getMessage(""+id);
			if(packet == null) {
				log.debug("Message not found {}",id);
				return;
			}
			packet.getIn().put(o).flip();
			packet.deserialize();
			
			this.dispatcher.dispatch(new InterClientEvent(server,packet));
		}
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception{
		IoBuffer buff = (IoBuffer) message;
		int id = buff.get();
		
		log.debug("Sending : {} from : {}",id ,session.getRemoteAddress());
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception{
		if(session.getAttribute(this) instanceof InterClient) {
			InterClient client = (InterClient)session.getAttribute(this);
			client.setState(ServerState.OFFLINE);
			client.setSession(null);
			session.removeAttribute(this);
			ClientFactory.refreshServer();
		}
		session.close(true);
	}
	
	@Override
	public void exceptionCaught(IoSession session,Throwable cause) throws Exception {
		if(cause.getCause() instanceof MessageNotFoundException) {
			log.error("Message not found : {}", cause.getMessage());
		}else if(Main.getConfigValue("devemu.options.other.debug").equals("true")){
			cause.printStackTrace();
		}
	}
}
