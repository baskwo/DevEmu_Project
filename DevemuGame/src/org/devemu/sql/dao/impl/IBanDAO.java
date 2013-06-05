package org.devemu.sql.dao.impl;

import java.util.Collection;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.entity.Ban;

public interface IBanDAO {
	public Ban find(long id);
    public Ban find(String name);
    public Ban findBy(String a, Object o);
    public Collection<Ban> findAll();

    public int preload();
    public Collection<Ban> load();
    public void unload();
    
    public void setFinder(String a, DAOFinder<Ban> finder);
    public DAOFinder<Ban> getFinder(String a);
    
    public void setLoader(String a, DAOLoader<Ban> loader);
    public DAOLoader<Ban> getLoader(String a);
    
    public void setPreloader(DAOPreloader<Ban> loader);
    public DAOPreloader<Ban> getPreloader();
}
