package org.devemu.sql;

import java.util.Collection;

public interface DAOFinder<T> {
    
    public T find(Object o);
    public Collection<T> findAll();
    
    public T add(T t);
    public void remove(T t);
    public void unload();
    
}
