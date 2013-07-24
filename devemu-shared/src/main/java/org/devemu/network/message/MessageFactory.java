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
		if(!cache.contains("", state)) {
			throw propagate(new MessageNotFoundException("ID : , State : "+state.name()));
		}
		Message o;
		try {
			o = cache.get("", state).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw propagate(e);
		}
		return o;
	}
	
	public Message getMessage(String id) {
		if(!cache.contains(id, State.NULL)) {
			throw propagate(new MessageNotFoundException("ID : " + id + ", State : null"));
		}
		Message o;
		try {
			o = cache.get(id, State.NULL).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw propagate(e);
		}
		return o;
	}
	
	public Message getMessage(String id,State state) throws MessageNotFoundException {
		if(!cache.contains(id, state)) {
			throw propagate(new MessageNotFoundException("ID : " + id + ", State : " + state.name()));
		}
		Message o;
		try {
			o = cache.get(id, state).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw propagate(e);
		}
		return o;
	}
}
