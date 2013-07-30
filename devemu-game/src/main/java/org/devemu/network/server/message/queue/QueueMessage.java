package org.devemu.network.server.message.queue;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;
import org.devemu.queue.QueueListener;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@Packet(id="Af")
public class QueueMessage extends Message {
	@Inject @Named("selection") QueueListener sListener;
	@Inject @Named("transfert") QueueListener tListener;
	
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
		if(state == State.TRANSFERT) {
			output = "Af" + currentPos + "|" + tListener.getQueueAbo().size() + "|" +
					tListener.getQueue().size() + "|" + (subscriber ? "1" : "0") + "|1";
		}else{
			output = "Af" + currentPos + "|" + sListener.getQueueAbo().size() + "|" +
					sListener.getQueue().size() + "|" + (subscriber ? "1" : "0") + "|2";
		}
	}

	@Override
	public void deserialize() {
	}
}
