package org.devemu.network.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.typesafe.config.Config;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.firewall.ConnectionThrottleFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.devemu.events.EventDispatcher;
import org.devemu.network.client.BaseClient.State;
import org.devemu.network.event.event.login.ClientLoginEvent;
import org.devemu.network.message.Message;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.network.server.client.RealmClient;
import org.devemu.network.server.message.connect.LoginConnectMessage;
import org.devemu.program.Main;
import org.devemu.services.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;

import static com.google.common.base.Throwables.propagate;

public class RealmServer extends IoHandlerAdapter implements Startable {
    private static final Logger log = LoggerFactory.getLogger(RealmServer.class);

	private static RealmServer instance;
	
	public static RealmServer getInstance() {
		if(instance == null)
			instance = Main.getInstanceFromInjector(RealmServer.class);
		return instance;
	}

    private NioSocketAcceptor acceptor;
    private final Config config;
    private EventDispatcher dispatcher;
	private MessageFactory factory;
	private Provider<RealmClient> clientProvider;

    @Inject
	public RealmServer(Config config,EventDispatcher dispatcher,MessageFactory factory, Provider<RealmClient> provider) {
    	this.dispatcher = dispatcher;
		this.factory = factory;
		this.clientProvider = provider;
        this.config = config;
        acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("throttle", new ConnectionThrottleFilter());
		//TODO: BanFilter acceptor.getFilterChain().addLast("ban", new BanFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"),
                LineDelimiter.NUL,
                new LineDelimiter("\n\0")
        )));
		acceptor.setHandler(this);
	}

    @Override
	public void start() {
		try {
			instance = this;
			acceptor.bind(new InetSocketAddress(config.getString("devemu.service.realm.addr"), config.getInt("devemu.service.realm.port")));
            log.debug("successfully started");
		} catch (IOException e) {
            log.error("start failure", e);
            throw propagate(e);
		}
	}

    @Override
    public void stop() {
        acceptor.unbind();
        acceptor.dispose(true);
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

	public NioSocketAcceptor getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(NioSocketAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	public Map<Long, IoSession> getManagedSessions() {
		return acceptor.getManagedSessions();
	}
}
