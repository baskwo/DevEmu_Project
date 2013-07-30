package org.devemu.utils.queue;

import org.devemu.events.EventDispatcher;
import org.devemu.network.client.BaseClient;
import org.devemu.network.event.event.login.ClientLoginEvent;
import org.devemu.network.message.MessageFactory;
import org.devemu.network.message.MessageNotFoundException;
import org.devemu.network.server.client.RealmClient;
import org.devemu.queue.QueueListener;
import org.devemu.sql.entity.manager.AccountManager;

import com.google.inject.Inject;

import static com.google.common.base.Throwables.propagate;

public class LoginQueueListener extends QueueListener {
	
	EventDispatcher dispatcher;
	MessageFactory factory;
	
	@Inject
	public LoginQueueListener(EventDispatcher dispatcher, MessageFactory factory) {
		this.dispatcher = dispatcher;
		this.factory = factory;
	}

	@Override
	public boolean add(BaseClient object) {
		boolean state = false;
		if(object instanceof RealmClient) {
			RealmClient client = (RealmClient) object;
			if(AccountManager.getAboTime(client.getAcc()) > 0) {
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
		if(object instanceof RealmClient) {
			RealmClient client = (RealmClient) object;
			int index = client.getQueue();
			boolean isSubscribe = AccountManager.getAboTime(client.getAcc()) > 0;
			if(isSubscribe) {
				queueAbo.remove(index);
				nextAbo--;
				for(BaseClient o : queueAbo) {
					if(o instanceof RealmClient) {
						RealmClient c = (RealmClient)o;
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
					if(o instanceof RealmClient) {
						RealmClient c = (RealmClient)o;
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
		if(o != null && o instanceof RealmClient) {
			RealmClient client = (RealmClient) o;
			remove(o);
			try {
				dispatcher.dispatch(new ClientLoginEvent(client,factory.getMessage("Al", client.getState())));
			} catch (MessageNotFoundException e) {
				throw propagate(e);
			} finally {
				state = true;
			}
		}
		return state;
	}
}
