package org.devemu.network.message;

import static com.google.common.base.Throwables.propagate;

import org.devemu.network.client.BaseClient.State;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class MessageFactory {
	private final Table<String, State, Class<? extends Message>> cache = HashBasedTable.create();
	
	public void addMessage(String id, State state, Class<? extends Message> messageClass) {
		cache.put(id, state, messageClass);
	}
	
	public Message getMessage(State state) {
		Message o;
		try {
			o = cache.get("", state).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw propagate(e);
		}
		return o;
	}
	
	public Message getMessage(String id,State state) {
		Message o;
		try {
			o = cache.get(id, state).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw propagate(e);
		}
		return o;
	}
}
