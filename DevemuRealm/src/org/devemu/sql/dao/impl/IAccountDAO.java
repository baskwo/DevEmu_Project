package org.devemu.sql.dao.impl;

import java.util.Collection;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.entity.Account;

public interface IAccountDAO {
    
    public Account find(long id);
    public Account find(String name);
    public Account findBy(String a, Object o);
    public Collection<Account> findAll();
    
    public void create(Account acc);
    public void update(Account acc);
    public void commit();
    
    public int preload();
    public Collection<Account> load();
    public void unload();
    
    public void setFinder(String a, DAOFinder<Account> finder);
    public DAOFinder<Account> getFinder(String a);
    
    public void setLoader(String a, DAOLoader<Account> loader);
    public DAOLoader<Account> getLoader(String a);
    
    public void setPreloader(DAOPreloader<Account> loader);
    public DAOPreloader<Account> getPreloader();
    
}
