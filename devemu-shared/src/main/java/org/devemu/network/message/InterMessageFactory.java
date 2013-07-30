package org.devemu.network.message;

import static com.google.common.base.Throwables.propagate;

import java.util.HashMap;

import com.google.common.collect.Maps;
import com.google.inject.Provider;

public class InterMessageFactory {
private final HashMap<String, Provider<? extends InterMessage>> cache = Maps.newHashMap();
	
	public void addMessage(String id, Provider<? extends InterMessage> messageClass) {
		cache.put(id, messageClass);
	}
	
	public InterMessage getMessage(String id) {
		if(!cache.containsKey(id)) {
			throw propagate(new MessageNotFoundException("ID : " + id));
		}
		InterMessage o = cache.get(id).get();
		return o;
	}
}
