package org.devemu.sql.dao.impl;

import java.util.Collection;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.entity.ExpStep;

public interface IExpDAO {
	public ExpStep find(long id);
    public Collection<ExpStep> findAll();

    public int preload();
    public Collection<ExpStep> load();
    public void unload();
    
    public void setFinder(String a, DAOFinder<ExpStep> finder);
    public DAOFinder<ExpStep> getFinder(String a);
    
    public void setLoader(String a, DAOLoader<ExpStep> loader);
    public DAOLoader<ExpStep> getLoader(String a);
    
    public void setPreloader(DAOPreloader<ExpStep> loader);
    public DAOPreloader<ExpStep> getPreloader();
}
