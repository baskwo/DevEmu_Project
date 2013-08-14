package org.devemu.network.server.message.queue;

import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

@Packet(id="Af")
public class QueueMessage extends Message {
	
	public int currentPos;
	public boolean subscriber;
	public int queueId;
	public int aboSize;
	public int nonAboSize;

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
		output = "Af" + currentPos + "|" + aboSize + "|" +
				nonAboSize + "|" + (subscriber ? "1" : "0") + "|" + queueId;
	}

	@Override
	public void deserialize() {
	}
}
