package org.devemu.sql;

import java.util.Collection;

public interface DAOUpdater<T> {
 
    public void update(Collection<T> t);
    
}
