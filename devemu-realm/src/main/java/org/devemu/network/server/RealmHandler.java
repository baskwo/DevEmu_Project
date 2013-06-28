package org.devemu.network.server;

import java.util.concurrent.TimeUnit;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.events.EventDispatcher;
import org.devemu.network.client.BaseClient.State;
import org.devemu.network.event.event.login.ClientLoginEvent;
import org.devemu.network.message.Message;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.server.client.RealmClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class RealmHandler extends IoHandlerAdapter{
	private static final Logger log = LoggerFactory.getLogger(RealmHandler.class);
	private EventDispatcher dispatcher;
	private MessageFactory factory;
	
	public RealmHandler(EventDispatcher dispatcher,MessageFactory factory) {
		this.dispatcher = dispatcher;
		this.factory = factory;
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception{
		RealmClient loc1 = new RealmClient(session);
		session.setAttribute(this, loc1);
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception{
		RealmClient loc1 = (RealmClient)session.getAttribute(this);
		Message o = this.factory.getMessage(loc1.getState());
		this.dispatcher.dispatch(new ClientLoginEvent(loc1,o));
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception{
		session.close(true);
	}
	
	@Override
	public void exceptionCaught(IoSession session,Throwable cause) throws Exception{
		cause.printStackTrace();
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		if(session.getAttribute(this) instanceof RealmClient) {
			RealmClient loc2 = (RealmClient)session.getAttribute(this);
			log.debug("Receiving : {} from : {}",loc1,session.getRemoteAddress().toString());
			Message o;
			if(loc2.getState() == State.SERVER)
				o = this.factory.getMessage(loc1.substring(0, 2),loc2.getState());
			else
				o = this.factory.getMessage(loc2.getState());
			o.setInput(loc1);
			o.deserialize();
			this.dispatcher.dispatch(new ClientLoginEvent(loc2,o));
		}
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		log.debug("Sending : {} to : {}",loc1,session.getRemoteAddress().toString());
	}
}
