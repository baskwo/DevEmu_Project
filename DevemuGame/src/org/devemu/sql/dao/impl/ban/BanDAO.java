package org.devemu.sql.dao.impl.ban;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.dao.impl.IBanDAO;
import org.devemu.sql.entity.Ban;

public class BanDAO implements IBanDAO {

    private final Map<String, DAOFinder<Ban>> finders = new HashMap<String, DAOFinder<Ban>>(4);
    private final Map<String, DAOLoader<Ban>> loaders = new HashMap<String, DAOLoader<Ban>>(4);
    
    private DAOPreloader<Ban> preloader = new BanDAOPreloader();
    
    private boolean load;
    private boolean preload;

    public BanDAO() {
        finders.put("id", new BanDAOFinder());
        
        loaders.put("id", new BanDAOLoader());
    }
    
    @Override
    public Ban find(long id) {
        Ban player = finders.get("id").find(id);
        if (player == null) {
            player = loaders.get("id").load(id);
            for (DAOFinder<Ban> finder : finders.values()) {
                finder.add(player);
            }
            return player;
        }
        return player;
    }

    @Override
    public Ban find(String name) {
        Ban player = finders.get("name").find(name);
        if (player == null) {
            player = loaders.get("name").load(name);
            for (DAOFinder<Ban> finder : finders.values()) {
                finder.add(player);
            }
            return player;
        }
        return player;
    }

    @Override
    public Ban findBy(String a, Object o) {
        System.out.println(a);
        Ban player = finders.get(a).find(o);
        if (player == null && loaders.containsKey(a)) {
            player = loaders.get(a).load(o);
            for (DAOFinder<Ban> finder : finders.values()) {
                finder.add(player);
            }
            return player;
        }
        return player;
    }

    @Override
    public Collection<Ban> findAll() {
        return finders.get("id").findAll();
    }

    @Override
    public int preload() {
        if (load || preload) {
            return 0;
        }
        preload = true;
        return preloader.preload();
    }

    @Override
    public Collection<Ban> load() {
        if (load) {
            return null;
        }
        
        Collection<Ban> accs;
        if (preload) {
            accs = preloader.load();
        } else {
            accs = loaders.get("id").loadAll();
        }
        
        if (accs == null) {
            return new ArrayList<Ban>(0);
        }
        
        for (Ban acc : accs) {
            for (DAOFinder<Ban> f : finders.values()) {
                f.add(acc);
            }
        }
        
        load = true;
        return accs;
    }

    @Override
    public void unload() {
        preloader.unload();
        for (DAOFinder<Ban> f : finders.values()) {
            f.unload();
        }
        preload = false;
        load = false;
    }

    @Override
    public void setFinder(String a, DAOFinder<Ban> finder) {
        if (finder == null) {
            if (finders.containsKey(a)) {
                finders.remove(a);
            }
            return;
        }
        finders.put(a, finder);
    }

    @Override
    public DAOFinder<Ban> getFinder(String a) {
        return finders.get(a);
    }

    @Override
    public void setLoader(String a, DAOLoader<Ban> loader) {
        if (loader == null) {
            if (loaders.containsKey(a)) {
                loaders.remove(a);
            }
            return;
        }
        
        loaders.put(a, loader);
    }

    @Override
    public DAOLoader<Ban> getLoader(String a) {
        return loaders.get(a);
    }

    @Override
    public void setPreloader(DAOPreloader<Ban> loader) {
        preloader = loader;
    }

    @Override
    public DAOPreloader<Ban> getPreloader() {
        return preloader;
    }

}
