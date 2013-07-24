package org.devemu.network.inter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.events.EventDispatcher;
import org.devemu.network.event.event.inter.InterClientEvent;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterMessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.network.server.message.inter.ConnectionInterMessage;
import org.devemu.program.Main;
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
			Main.log("Receiving : " + id + " from : " + session.getRemoteAddress(), InterHandler.class);
			
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
		Main.log("Sending : " + id + " to : " + session.getRemoteAddress(), InterHandler.class);
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
