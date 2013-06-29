package org.devemu.network.message;

import static com.google.common.base.Throwables.propagate;

import java.util.HashMap;

import com.google.common.collect.Maps;

public class InterMessageFactory {
private final HashMap<String, Class<? extends InterMessage>> cache = Maps.newHashMap();
	
	public void addMessage(String id, Class<? extends InterMessage> messageClass) {
		cache.put(id, messageClass);
	}
	
	public InterMessage getMessage(String id) {
		InterMessage o;
		try {
			o = cache.get(id).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw propagate(e);
		}
		return o;
	}
}
