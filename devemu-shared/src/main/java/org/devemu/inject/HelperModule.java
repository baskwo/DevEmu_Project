package org.devemu.inject;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;

public abstract class HelperModule extends AbstractModule {
	
	List<Class<?>> helpers = Lists.newArrayList();
	
	public abstract void initialize();

	@Override
	protected void configure() {
		for(Class<?> o : helpers) {
			bind(o);
		}
	}
	
	protected void add(Class<?> helper) {
		helpers.add(helper);
	}
}
