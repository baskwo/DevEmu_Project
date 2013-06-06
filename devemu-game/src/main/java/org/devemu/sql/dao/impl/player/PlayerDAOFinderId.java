package org.devemu.sql.dao.impl.player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.entity.Player;

public class PlayerDAOFinderId implements DAOFinder<Player> {
    
    private final Map<Long, Player> players = new HashMap<Long, Player>(512);
    private final Collection<Player> synchronizedplayers = Collections.synchronizedCollection(Collections.unmodifiableCollection(players.values()));

    @Override
    public Player find(Object o) {
        if (o instanceof Long) {
            return players.get(o);
        }
        throw new IllegalArgumentException("PlayerDAOFinderId.find(Object o) : o must be a Long.");
    }

    @Override
    public Collection<Player> findAll() {
        return synchronizedplayers;
    }

    @Override
    public Player add(Player player) {
        if (player == null) {
            return null;
        }
        
        long id = player.getGuid();
        if (!players.containsKey(id)) {
            players.put(id, player);
        }
        return player;
    }

    @Override
    public void remove(Player player) {
        if (player == null) {
            return;
        }
        
        long id = player.getGuid();
        if (players.containsKey(id)) {
            players.remove(id);
        }
    }

    @Override
    public void unload() {
        players.clear();
    }
    
}
