package org.devemu.sql;

import java.util.Collection;

public interface DAOPreloader<T> {
    
    public int preload();
    public Collection<T> load();
    public void unload();
    
    public void setGetDb(GetDatabase getDb);
    
}
