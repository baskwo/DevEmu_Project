package org.devemu.sql;

import java.util.Collection;

public interface DAOLoader<T> {
    
    public T load(Object o);
    public Collection<T> loadAll();
    
    public void setGetDb(GetDatabase getDb);
    
}
