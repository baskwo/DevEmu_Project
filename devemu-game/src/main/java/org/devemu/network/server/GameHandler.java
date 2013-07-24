package org.devemu.network.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.events.EventDispatcher;
import org.devemu.network.event.event.game.GameClientEvent;
import org.devemu.network.message.Message;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.message.MessageNotFoundException;
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
		GameClient client = new GameClient(session);
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
			Main.log("Receiving : " + in + " from : " + session.getRemoteAddress().toString(), GameHandler.class);
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
		Main.log("Sending : " + out + " to : " + session.getRemoteAddress().toString(), GameHandler.class);
	}
}
