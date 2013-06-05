package org.devemu.sql.dao.impl.ban;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.entity.Ban;

public class BanDAOFinder implements DAOFinder<Ban> {
    
    private final Map<Long, Ban> bans = new HashMap<Long, Ban>(512);
    private final Collection<Ban> synchronizedbans = Collections.synchronizedCollection(Collections.unmodifiableCollection(bans.values()));

    @Override
    public Ban find(Object o) {
        if (o instanceof Long) {
            return bans.get(o);
        }
        throw new IllegalArgumentException("BanDAOFinderId.find(Object o) : o must be a Long.");
    }

    @Override
    public Collection<Ban> findAll() {
        return synchronizedbans;
    }

    @Override
    public Ban add(Ban ban) {
        if (ban == null) {
            return null;
        }
        
        long id = ban.getId();
        if (!bans.containsKey(id)) {
            bans.put(id, ban);
        }
        return ban;
    }

    @Override
    public void remove(Ban ban) {
        if (ban == null) {
            return;
        }
        
        long id = ban.getId();
        if (bans.containsKey(id)) {
            bans.remove(id);
        }
    }

    @Override
    public void unload() {
        bans.clear();
    }
    
}
