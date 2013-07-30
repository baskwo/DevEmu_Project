package org.devemu.queue;

import java.util.ArrayList;
import java.util.List;

import org.devemu.network.client.BaseClient;

public abstract class QueueListener {
	protected int nextAbo = 0;
	protected int next = 0;
	protected List<BaseClient> queue = new ArrayList<BaseClient>();
	protected List<BaseClient> queueAbo = new ArrayList<BaseClient>();
	
	public abstract boolean add(BaseClient object);
	public abstract boolean remove(BaseClient object);
	
	public abstract boolean invoke(BaseClient object);
	
	public BaseClient getFirst() {
		BaseClient o = null;
		if(!queueAbo.isEmpty())
			o = queueAbo.get(0);
		else if(!queue.isEmpty())
			o = queue.get(0);
		return o;
	}

	public boolean isEmpty() {
		if(queueAbo.isEmpty() && queue.isEmpty())
			return true;
		return false;
	}
	
	public int getNextAbo() {
		return nextAbo;
	}
	public void setNextAbo(int nextAbo) {
		this.nextAbo = nextAbo;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public List<BaseClient> getQueue() {
		return queue;
	}
	public void setQueue(List<BaseClient> queue) {
		this.queue = queue;
	}
	public List<BaseClient> getQueueAbo() {
		return queueAbo;
	}
	public void setQueueAbo(List<BaseClient> queueAbo) {
		this.queueAbo = queueAbo;
	}
}
