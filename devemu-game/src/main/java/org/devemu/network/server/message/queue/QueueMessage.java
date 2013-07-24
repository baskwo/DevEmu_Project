package org.devemu.network.server.message.queue;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.program.Main;
import org.devemu.utils.queue.QueueSelector;

@Packet(id="Af")
public class QueueMessage extends Message {
	
	public int currentPos;
	public boolean subscriber;
	public State state;

	public boolean isSubscriber() {
		return subscriber;
	}

	public void setSubscriber(boolean subscriber) {
		this.subscriber = subscriber;
	}

	public int getCurrentPos() {
		return currentPos;
	}

	public void setCurrentPos(int currentPos) {
		this.currentPos = currentPos;
	}

	@Override
	public void serialize() {
		output = "Af" + currentPos + "|" + QueueSelector.getSelector(state).getTotAbo() + "|" +
				QueueSelector.getSelector(state).getTotNonAbo() + "|" + (subscriber ? "1" : "0") + "|" + Main.getGuid();
	}

	@Override
	public void deserialize() {
	}
}
