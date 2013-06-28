package org.devemu.inject;

import static com.google.common.base.Throwables.propagate;

import java.io.IOException;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.network.message.MessageFactory;

import com.google.common.reflect.ClassPath;
import com.google.inject.AbstractModule;

public class MessageModule extends AbstractModule{
	MessageFactory factory;
	
	public static MessageModule of(MessageFactory factory,ClassLoader loader) {
		return new MessageModule(factory,loader);
	}
	
	public MessageModule(MessageFactory factory,ClassLoader loader) {
		this.factory = factory;
		try {
			init(ClassPath.from(loader));
		} catch (IOException e) {
			throw propagate(e);
		}
	}
	
	@Override
	protected void configure() {
		bind(MessageFactory.class).toInstance(factory);
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
                		factory.addMessage(id, state, messageClass);
                	}else{
                		continue;
                	}
                }
            }
		}
	}
}
