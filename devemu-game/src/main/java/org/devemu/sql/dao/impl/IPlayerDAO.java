package org.devemu.sql.dao.impl;

import java.util.Collection;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.entity.Player;

public interface IPlayerDAO {
	public Player find(long id);
	public Player find(String name);
	public Player findBy(String a, Object o);
	public Collection<Player> findAll();
	
	public int getNextID();
	    
	public void create(Player acc);
	public void update(Player acc);
	public void delete(Player acc);
	public void commit();
	    
	public int preload();
	public Collection<Player> load();
	public void unload();
	    
	public void setFinder(String a, DAOFinder<Player> finder);
	public DAOFinder<Player> getFinder(String a);
	    
	public void setLoader(String a, DAOLoader<Player> loader);
	public DAOLoader<Player> getLoader(String a);
	    
	public void setPreloader(DAOPreloader<Player> loader);
	public DAOPreloader<Player> getPreloader();
}
