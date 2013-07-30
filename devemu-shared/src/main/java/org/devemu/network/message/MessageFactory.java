package org.devemu.network.message;

import static com.google.common.base.Throwables.propagate;

import org.devemu.network.client.BaseClient.State;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Provider;

public class MessageFactory {
	private final Table<String, State, Provider<? extends Message>> cache = HashBasedTable.create();
	
	public void addMessage(String id, State state, Provider<? extends Message> messageClass) {
		cache.put(id, state, messageClass);
	}
	
	public Message getMessage(State state) {
		if(!cache.contains("", state)) {
			throw propagate(new MessageNotFoundException("ID : , State : "+state.name()));
		}
		Message o = cache.get("", state).get();
		return o;
	}
	
	public Message getMessage(String id) {
		if(!cache.contains(id, State.NULL)) {
			throw propagate(new MessageNotFoundException("ID : " + id + ", State : null"));
		}
		Message o = cache.get(id, State.NULL).get();
		return o;
	}
	
	public Message getMessage(String id,State state) throws MessageNotFoundException {
		if(!cache.contains(id, state)) {
			throw propagate(new MessageNotFoundException("ID : " + id + ", State : " + state.name()));
		}
		Message o = cache.get(id, state).get();
		return o;
	}
}
