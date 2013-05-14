package org.devemu.sql;

import java.util.Collection;

public interface DAODeleter<T> {
    
    public void delete(Collection<T> t);
    
}
