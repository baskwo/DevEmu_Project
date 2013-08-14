package org.devemu.network.inter;

import static com.google.common.base.Throwables.propagate;

import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.devemu.events.EventDispatcher;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterMessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.network.server.message.inter.ConnectionInterMessage;
import org.devemu.program.Main;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class InterServer extends IoHandlerAdapter implements Startable {
	private static final Logger log = LoggerFactory.getLogger(InterServer.class);
	
	private NioSocketConnector connector;
	private EventDispatcher dispatcher;
	private InterMessageFactory factory;
	
	@Inject
	private InterServer(EventDispatcher dispatcher,InterMessageFactory factory) {
		this.dispatcher = dispatcher;
		this.factory = factory;
		connector = new NioSocketConnector();
		connector.setHandler(this);
	}
	
	@Override
	public void start() {
		try {
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
	
	public void sessionCreated(IoSession session) throws Exception {
		InterClient client = new InterClient(session);
		session.setAttribute(this,client);
		
		ConnectionInterMessage o = new ConnectionInterMessage();
		o.serialize();
		session.write(o.out);
	}
	
	public void messageReceived(IoSession session, Object message) throws Exception {
		if(message instanceof IoBuffer) {
			IoBuffer o = (IoBuffer)message;
			int id = o.get();
			log.debug("Receiving : {} from : {}", id, session.getRemoteAddress());
			
			InterClient client = (InterClient) session.getAttribute(this);
			
			InterMessage packet = factory.getMessage(""+id);
			packet.getIn().put(o).flip();
			packet.deserialize();
			
			this.dispatcher.dispatch(new InterClientEvent(client,packet));
		}
	}
	
	public void messageSent(IoSession session, Object message) throws Exception {
		IoBuffer buff = (IoBuffer) message;
		int id = buff.get();
		log.debug("Sending : {} from : {}", id, session.getRemoteAddress());
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		session.close(true);
	}
	
	public void exceptionCaught(IoSession session,Throwable cause) throws Exception {
		if(cause.getCause() instanceof MessageNotFoundException) {
			log.error("Message not found : {}", cause.getMessage());
		}else if(Main.getConfigValue("devemu.options.other.debug").equals("true")){
			cause.printStackTrace();
		}
	}
}
