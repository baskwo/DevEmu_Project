package org.devemu.inject;

import org.devemu.queue.QueueListener;
import org.devemu.utils.Pair;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public class QueueModule extends AbstractModule {
	Pair<String,Class<? extends QueueListener>>[] listeners;
	
	public QueueModule(Pair<String,Class<? extends QueueListener>>... listeners) {
		this.listeners = listeners;
	}
	
	public static QueueModule of(Pair<String,Class<? extends QueueListener>>... listeners) {
		return new QueueModule(listeners);
	}

	@Override
	protected void configure() {
		for(Pair<String,Class<? extends QueueListener>> o : listeners) {
			bind(QueueListener.class).annotatedWith(Names.named(o.getO1())).to(o.getO2()).in(Scopes.SINGLETON);
		}
	}
}
