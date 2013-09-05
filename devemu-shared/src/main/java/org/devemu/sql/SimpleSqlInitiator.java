package org.devemu.sql;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class SimpleSqlInitiator {
	
	@Inject PersistService service;
	
	public void initiate() {
        service.start(); 
	} 
}
