package org.devemu.network.server;

import static com.google.common.base.Throwables.propagate;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.events.EventDispatcher;
import org.devemu.network.event.event.game.GameClientEvent;
import org.devemu.network.message.Message;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.server.client.GameClient;
import org.devemu.network.server.message.connect.ClientConnectMessage;
import org.devemu.program.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameHandler extends IoHandlerAdapter{
	private static final Logger log = LoggerFactory.getLogger(GameHandler.class);
	private EventDispatcher dispatcher;
	private MessageFactory factory;
	
	public GameHandler(EventDispatcher dispatcher,MessageFactory factory) {
		this.dispatcher = dispatcher;
		this.factory = factory;
	}
	
	public void sessionCreated(IoSession session) throws Exception{
		GameClient loc1 = new GameClient(session);
		session.setAttribute("client", loc1);
		
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
	
	public void messageReceived(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		session.setAttribute("lastMessage",loc1.substring(0, 2));
		if(session.getAttribute("client") instanceof GameClient) {
			GameClient loc2 = (GameClient)session.getAttribute("client");
			Main.log("Receiving : " + loc1 + " from : " + session.getRemoteAddress().toString(), GameHandler.class);
			Message o = factory.getMessage(loc1.substring(0, 2));
			if(o == null) {
				log.debug("Message not found {}",loc1.substring(0,2));
				return;
			}
			o.setInput(loc1);
			o.deserialize();
			dispatcher.dispatch(new GameClientEvent(loc2,o));
		}
	}
	
	public void messageSent(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		Main.log("Sending : " + loc1 + " to : " + session.getRemoteAddress().toString(), GameHandler.class);
	}
}
