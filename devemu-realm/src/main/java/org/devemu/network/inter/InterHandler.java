package org.devemu.network.inter;

import static com.google.common.base.Throwables.propagate;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.events.EventDispatcher;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.inter.client.ClientFactory;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterMessageFactory;
import org.devemu.program.Main;
import org.devemu.utils.enums.ServerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterHandler extends IoHandlerAdapter{
	private static final Logger log = LoggerFactory.getLogger(InterHandler.class);
	private EventDispatcher dispatcher;
	private InterMessageFactory factory;
	
	public InterHandler(EventDispatcher dispatcher,InterMessageFactory factory) {
		this.dispatcher = dispatcher;
		this.factory = factory;
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception{
		if(message instanceof IoBuffer) {
			IoBuffer o = (IoBuffer)message;
			int id = o.get();
			session.setAttribute("lastMessage",id);
			int serv = o.get();
			Main.log("Receiving : " + id + " from : " + session.getRemoteAddress(), InterHandler.class);
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
		IoBuffer loc1 = (IoBuffer) message;
		int loc2 = loc1.get();
		
		Main.log("Sending : " + loc2 + " to : " + session.getRemoteAddress(), InterHandler.class);
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
		boolean isMessageError = false;
		for(StackTraceElement e : cause.getStackTrace()) {
			if(e.getMethodName().contains("getMessage")) {
				isMessageError = true;
				break;
			}
		}
		if(isMessageError)
			log.error("Message not found : {}",session.getAttribute("lastMessage"));
		else
			throw propagate(cause);
	}
}
