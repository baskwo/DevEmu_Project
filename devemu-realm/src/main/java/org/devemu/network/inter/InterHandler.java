package org.devemu.network.inter;

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

public class InterHandler extends IoHandlerAdapter{
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
			Main.log("Receiving : " + id + " from : " + session.getRemoteAddress(), InterHandler.class);
			InterClient server = ClientFactory.get(o.get());
			InterMessage packet = factory.getMessage(""+id);
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
		if(session.getAttribute("client") instanceof InterClient) {
			((InterClient)session.getAttribute("client")).setState(ServerState.OFFLINE);
			ClientFactory.refreshServer();
		}
		session.close(true);
	}
	
	@Override
	public void exceptionCaught(IoSession session,Throwable cause) throws Exception {
		
	}
}
