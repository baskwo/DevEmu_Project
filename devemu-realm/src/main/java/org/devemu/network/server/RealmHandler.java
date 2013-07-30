package org.devemu.network.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.events.EventDispatcher;
import org.devemu.network.client.BaseClient.State;
import org.devemu.network.event.event.login.ClientLoginEvent;
import org.devemu.network.message.Message;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.network.server.client.RealmClient;
import org.devemu.network.server.message.connect.LoginConnectMessage;
import org.devemu.program.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class RealmHandler extends IoHandlerAdapter{
	private static final Logger log = LoggerFactory.getLogger(RealmHandler.class);
	private EventDispatcher dispatcher;
	private MessageFactory factory;
	private Provider<RealmClient> clientProvider;
	
	@Inject
	public RealmHandler(EventDispatcher dispatcher,MessageFactory factory, Provider<RealmClient> provider) {
		this.dispatcher = dispatcher;
		this.factory = factory;
		this.clientProvider = provider;
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception{
		RealmClient client = clientProvider.get();
		client.setSession(session);
		session.setAttribute(this, client);
		LoginConnectMessage o = new LoginConnectMessage();
		o.salt = client.getSalt();
		o.serialize();
		client.setState(State.VERSION);
		client.write(o.output);
	}
	
	@Override
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
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception{
		String in = message.toString();
		if(session.getAttribute(this) instanceof RealmClient) {
			RealmClient client = (RealmClient)session.getAttribute(this);
			log.debug("Receiving : {} from : {}",in,session.getRemoteAddress().toString());
			Message o;
			if(client.getState() == State.SERVER)
				o = this.factory.getMessage(in.substring(0, 2),client.getState());
			else
				o = this.factory.getMessage(client.getState());
			o.setInput(in);
			o.deserialize();
			this.dispatcher.dispatch(new ClientLoginEvent(client,o));
		}
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception{
		String out = message.toString();
		log.debug("Sending : {} to : {}",out,session.getRemoteAddress().toString());
	}
}
