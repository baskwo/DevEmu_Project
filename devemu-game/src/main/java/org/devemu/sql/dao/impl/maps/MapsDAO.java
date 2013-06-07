package org.devemu.sql.dao.impl.maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.dao.impl.IMapsDAO;
import org.devemu.sql.entity.Maps;

public class MapsDAO implements IMapsDAO {

    private final Map<String, DAOFinder<Maps>> finders = new HashMap<String, DAOFinder<Maps>>(4);
    private final Map<String, DAOLoader<Maps>> loaders = new HashMap<String, DAOLoader<Maps>>(4);
    
    private DAOPreloader<Maps> preloader = new MapsDAOPreloader();
    
    private boolean load;
    private boolean preload;

    public MapsDAO() {
        finders.put("id", new MapsDAOFinderId());
       
        loaders.put("id", new MapsDAOLoaderId());
    }
    
    @Override
    public Maps find(long id) {
        Maps maps = finders.get("id").find(id);
        if (maps == null) {
            maps = loaders.get("id").load(id);
            for (DAOFinder<Maps> finder : finders.values()) {
                finder.add(maps);
            }
            return maps;
        }
        return maps;
    }

    @Override
    public Collection<Maps> findAll() {
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
    public Collection<Maps> load() {
        if (load) {
            return null;
        }
        
        Collection<Maps> accs;
        if (preload) {
            accs = preloader.load();
        } else {
            accs = loaders.get("id").loadAll();
        }
        
        if (accs == null) {
            return new ArrayList<Maps>(0);
        }
        
        for (Maps acc : accs) {
            for (DAOFinder<Maps> f : finders.values()) {
                f.add(acc);
            }
        }
        
        load = true;
        return accs;
    }

    @Override
    public void unload() {
        preloader.unload();
        for (DAOFinder<Maps> f : finders.values()) {
            f.unload();
        }
        preload = false;
        load = false;
    }

    @Override
    public void setFinder(String a, DAOFinder<Maps> finder) {
        if (finder == null) {
            if (finders.containsKey(a)) {
                finders.remove(a);
            }
            return;
        }
        finders.put(a, finder);
    }

    @Override
    public DAOFinder<Maps> getFinder(String a) {
        return finders.get(a);
    }

    @Override
    public void setLoader(String a, DAOLoader<Maps> loader) {
        if (loader == null) {
            if (loaders.containsKey(a)) {
                loaders.remove(a);
            }
            return;
        }
        
        loaders.put(a, loader);
    }

    @Override
    public DAOLoader<Maps> getLoader(String a) {
        return loaders.get(a);
    }

    @Override
    public void setPreloader(DAOPreloader<Maps> loader) {
        preloader = loader;
    }

    @Override
    public DAOPreloader<Maps> getPreloader() {
        return preloader;
    }
}
