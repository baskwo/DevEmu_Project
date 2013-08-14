package org.devemu.inject;

import static com.google.common.base.Throwables.propagate;

import java.io.IOException;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterMessageFactory;
import org.devemu.network.message.InterPacket;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.network.message.MessageFactory;

import com.google.common.reflect.ClassPath;
import com.google.inject.AbstractModule;

public class MessageModule extends AbstractModule{
	MessageFactory factory;
	InterMessageFactory iFactory;
	ClassPath path;
	
	public static MessageModule of(MessageFactory factory,InterMessageFactory iFactory,ClassLoader loader) {
		return new MessageModule(factory,iFactory,loader);
	}
	
	public MessageModule(MessageFactory factory,InterMessageFactory iFactory,ClassLoader loader) {
		this.factory = factory;
		this.iFactory = iFactory;
		try {
			path = ClassPath.from(loader);
		} catch (IOException e) {
			throw propagate(e);
		}
	}
	
	@Override
	protected void configure() {
		bind(MessageFactory.class).toInstance(factory);
		bind(InterMessageFactory.class).toInstance(iFactory);
		init(path);
	}
	
	@SuppressWarnings("unchecked")
	private void init(ClassPath classPath) {
		for (ClassPath.ResourceInfo info : classPath.getTopLevelClassesRecursive("org.devemu.network.server.message")) {
			if (info instanceof ClassPath.ClassInfo) {
            	ClassPath.ClassInfo classInfo = (ClassPath.ClassInfo) info;
                Class<?> eventClass = classInfo.load();
                if(Message.class.isAssignableFrom(eventClass)) {
                	Class<? extends Message> messageClass = (Class<? extends Message>) eventClass;
                	Packet packet = messageClass.getAnnotation(Packet.class);
                	if(packet != null) {
                		String id = packet.id();
                		State state = packet.state();
                		factory.addMessage(id, state, getProvider(messageClass));
                	}else{
                		continue;
                	}
                }else if(InterMessage.class.isAssignableFrom(eventClass)) {
                	Class<? extends InterMessage> messageClass = (Class<? extends InterMessage>) eventClass;
                	InterPacket packet = messageClass.getAnnotation(InterPacket.class);
                	if(packet != null) {
                		String id = packet.id();
                		iFactory.addMessage(id, getProvider(messageClass));
                	}else{
                		continue;
                	}
                }
            }
		}
	}
}
