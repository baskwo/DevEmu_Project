package org.devemu.sql.dao.impl;

import java.util.Collection;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.entity.Maps;

public interface IMapsDAO {
	public Maps find(long id);
    public Collection<Maps> findAll();

    public int preload();
    public Collection<Maps> load();
    public void unload();
    
    public void setFinder(String a, DAOFinder<Maps> finder);
    public DAOFinder<Maps> getFinder(String a);
    
    public void setLoader(String a, DAOLoader<Maps> loader);
    public DAOLoader<Maps> getLoader(String a);
    
    public void setPreloader(DAOPreloader<Maps> loader);
    public DAOPreloader<Maps> getPreloader();
}
