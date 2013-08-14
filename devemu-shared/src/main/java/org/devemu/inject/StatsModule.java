package org.devemu.inject;

import org.devemu.file.FileProvider;
import org.devemu.utils.Pair;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class StatsModule extends AbstractModule {
	
	Pair<String,FileProvider>[] provider;
	
	public StatsModule(Pair<String,FileProvider>... provider) {
		this.provider = provider;
	}
	
	public static StatsModule of(Pair<String,FileProvider>... provider) {
		return new StatsModule(provider);
	}

	@Override
	protected void configure() {
		for(Pair<String,FileProvider> o : provider) {
			bind(FileProvider.class).annotatedWith(Names.named(o.getO1())).toInstance(o.getO2());
		}
	}
}
