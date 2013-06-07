package org.devemu.sql.dao.impl.maps;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.entity.Maps;

public class MapsDAOFinderId implements DAOFinder<Maps> {
    
    private final Map<Long, Maps> maps = new HashMap<Long, Maps>(512);
    private final Collection<Maps> synchronizedmaps = Collections.synchronizedCollection(Collections.unmodifiableCollection(maps.values()));

    @Override
    public Maps find(Object o) {
        if (o instanceof Long) {
            return maps.get(o);
        }
        throw new IllegalArgumentException("mapsDAOFinderId.find(Object o) : o must be a Long.");
    }

    @Override
    public Collection<Maps> findAll() {
        return synchronizedmaps;
    }

    @Override
    public Maps add(Maps map) {
        if (map == null) {
            return null;
        }
        
        long id = map.getId();
        if (!maps.containsKey(id)) {
            maps.put(id, map);
        }
        return map;
    }

    @Override
    public void remove(Maps map) {
        if (map == null) {
            return;
        }
        
        long id = map.getId();
        if (maps.containsKey(id)) {
            maps.remove(id);
        }
    }

    @Override
    public void unload() {
        maps.clear();
    }
    
}
