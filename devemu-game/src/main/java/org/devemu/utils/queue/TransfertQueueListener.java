package org.devemu.utils.queue;

import org.devemu.events.EventDispatcher;
import org.devemu.network.client.BaseClient;
import org.devemu.network.client.BaseClient.State;
import org.devemu.network.event.event.game.GameClientReuseEvent;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.network.server.client.GameClient;
import org.devemu.queue.QueueListener;
import org.devemu.utils.helper.AccountHelper;

import com.google.inject.Inject;

import static com.google.common.base.Throwables.propagate;

public class TransfertQueueListener extends QueueListener {
	AccountHelper accHelper;
	EventDispatcher dispatcher;
	MessageFactory factory;
	
	@Inject
	public TransfertQueueListener(AccountHelper aHelper,EventDispatcher dispatcher, MessageFactory factory) {
		accHelper = aHelper;
		this.dispatcher = dispatcher;
		this.factory = factory;
	}

	@Override
	public boolean add(BaseClient object) {
		boolean state = false;
		if(object instanceof GameClient) {
			GameClient client = (GameClient) object;
			if(accHelper.getAboTime(client.getAcc()) > 0) {
				queueAbo.add(object);
				client.setQueue(nextAbo);
				nextAbo++;
			}
			else {
				queue.add(object);
				client.setQueue(next);
				next++;
			}
			state = true;
		}
		return state;
	}

	@Override
	public boolean remove(BaseClient object) {
		boolean state = false;
		if(object instanceof GameClient) {
			GameClient client = (GameClient) object;
			int index = client.getQueue();
			boolean isSubscribe = accHelper.getAboTime(client.getAcc()) > 0;
			if(isSubscribe) {
				queueAbo.remove(index);
				nextAbo--;
				for(BaseClient o : queueAbo) {
					if(o instanceof GameClient) {
						GameClient c = (GameClient)o;
						if(c.getQueue() < index)
							continue;
						else if(c.getQueue() == index)
							c.setQueue(0);
						else {
							int pos = c.getQueue();
							c.setQueue(pos--);
						}
					}
				}
			}else{
				queue.remove(index);
				next--;
				for(BaseClient o : queue) {
					if(o instanceof GameClient) {
						GameClient c = (GameClient)o;
						if(c.getQueue() < index)
							continue;
						else if(c.getQueue() == index)
							c.setQueue(0);
						else {
							int pos = c.getQueue();
							c.setQueue(pos--);
						}
					}
				}
			}
			state = true;
		}
		return state;
	}

	@Override
	public boolean invoke(BaseClient o) {
		boolean state = false;
		if(o != null && o instanceof GameClient) {
			GameClient client = (GameClient) o;
			remove(o);
			try {
				dispatcher.dispatch(new GameClientReuseEvent(client,factory.getMessage("AL", State.NULL)));
			} catch (MessageNotFoundException e) {
				throw propagate(e);
			} finally {
				state = true;
			}
		}
		return state;
	}
}
