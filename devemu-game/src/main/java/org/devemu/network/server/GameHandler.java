package org.devemu.network.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.events.EventDispatcher;
import org.devemu.network.event.event.game.GameClientEvent;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.server.client.GameClient;
import org.devemu.program.Main;

public class GameHandler extends IoHandlerAdapter{
	private EventDispatcher dispatcher;
	private MessageFactory factory;
	
	public GameHandler(EventDispatcher dispatcher,MessageFactory factory) {
		this.dispatcher = dispatcher;
		this.factory = factory;
	}
	
	public void sessionCreated(IoSession session) throws Exception{
		GameClient loc1 = new GameClient(session);
		session.setAttribute("client", loc1);
		
		/*Packet loc3 = new Packet();
		loc3.setIdentificator("HG");
		loc1.write(loc3.toString());*/
	}
	
	public void sessionClosed(IoSession session) throws Exception{
		session.close(true);
	}
	
	public void messageReceived(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		if(session.getAttribute("client") instanceof GameClient) {
			GameClient loc2 = (GameClient)session.getAttribute("client");
			Main.log("Receiving : " + loc1 + " from : " + session.getRemoteAddress().toString(), GameHandler.class);
			dispatcher.dispatch(new GameClientEvent(loc2,factory.getMessage(loc1.substring(0, 2))));
		}
	}
	
	public void messageSent(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		Main.log("Sending : " + loc1 + " to : " + session.getRemoteAddress().toString(), GameHandler.class);
	}
}
