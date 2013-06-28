package org.devemu.network.server.message.queue;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.utils.queue.QueueSelector;

@Packet(id="Af",state = State.SERVER)
public class QueueMessage extends Message {
	
	public int currentPos;
	public boolean subscriber;

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
		output = "Af" + currentPos + "|" + QueueSelector.getTotAbo() + "|" +
				QueueSelector.getTotNonAbo() + "|" + (subscriber ? "1" : "0") + "|1";
	}

	@Override
	public void deserialize() {
	}
}
