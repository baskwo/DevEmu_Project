package org.devemu.inject;

import org.devemu.queue.QueueListener;
import org.devemu.queue.QueueObject;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public class QueueModule extends AbstractModule {
	QueueObject[] listeners;
	
	public QueueModule(QueueObject... listeners) {
		this.listeners = listeners;
	}
	
	public static QueueModule of(QueueObject... listeners) {
		return new QueueModule(listeners);
	}

	@Override
	protected void configure() {
		for(QueueObject o : listeners) {
			bind(QueueListener.class).annotatedWith(Names.named(o.annot)).to(o.classe).in(Scopes.SINGLETON);
		}
	}
}
