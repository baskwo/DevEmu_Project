<<<<<<< HEAD:DevemuGame/src/org/devemu/sql/dao/impl/player/PlayerDAOFinderName.java
package org.devemu.sql.dao.impl.player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.entity.Player;

public class PlayerDAOFinderName implements DAOFinder<Player> {
    
    private final Map<String, Player> players = new HashMap<>(512);
    private final Collection<Player> synchronizedplayers = Collections.synchronizedCollection(Collections.unmodifiableCollection(players.values()));

    @Override
    public Player find(Object o) {
        if (o instanceof String) {
            return players.get(o.toString());
        }
        throw new IllegalArgumentException("PlayerDAOFinderName.find(Object o) : o must be a String.");
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
        
        String name = player.getName();
        if (!players.containsKey(name)) {
            players.put(name, player);
        }
        return player;
    }

    @Override
    public void remove(Player player) {
        if (player == null) {
            return;
        }
        
        String name = player.getName();
        if (players.containsKey(name)) {
            players.remove(name);
        }
    }

    @Override
    public void unload() {
        players.clear();
    }
    
}
=======
package org.devemu.sql.dao.impl.player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.entity.Player;

public class PlayerDAOFinderName implements DAOFinder<Player> {
    
    private final Map<String, Player> players = new HashMap<String, Player>(512);
    private final Collection<Player> synchronizedplayers = Collections.synchronizedCollection(Collections.unmodifiableCollection(players.values()));

    @Override
    public Player find(Object o) {
        if (o instanceof String) {
            return players.get(o.toString());
        }
        throw new IllegalArgumentException("PlayerDAOFinderName.find(Object o) : o must be a String.");
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
        
        String name = player.getName();
        if (!players.containsKey(name)) {
            players.put(name, player);
        }
        return player;
    }

    @Override
    public void remove(Player player) {
        if (player == null) {
            return;
        }
        
        String name = player.getName();
        if (players.containsKey(name)) {
            players.remove(name);
        }
    }

    @Override
    public void unload() {
        players.clear();
    }
    
}
>>>>>>> 800500b6eda7a3b433414a80b9c5ef278d93b04e:devemu-game/src/main/java/org/devemu/sql/dao/impl/player/PlayerDAOFinderName.java
