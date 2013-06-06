package org.devemu.sql;

import java.util.Collection;

public interface DAOCreator<T> {
    
    public void create(Collection<T> t);
    
}
