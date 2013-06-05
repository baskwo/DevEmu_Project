package org.devemu.sql.dao.impl.exp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.dao.impl.IExpDAO;
import org.devemu.sql.entity.ExpStep;

public class ExpDAO implements IExpDAO {

    private final Map<String, DAOFinder<ExpStep>> finders = new HashMap<String, DAOFinder<ExpStep>>(4);
    private final Map<String, DAOLoader<ExpStep>> loaders = new HashMap<String, DAOLoader<ExpStep>>(4);
    
    private DAOPreloader<ExpStep> preloader = new ExpDAOPreloader();
    
    private boolean load;
    private boolean preload;

    public ExpDAO() {
        finders.put("level", new ExpDAOFinderLevel());
       
        loaders.put("level", new ExpDAOLoaderLevel());
    }
    
    @Override
    public ExpStep find(long id) {
        ExpStep player = finders.get("level").find(id);
        if (player == null) {
            player = loaders.get("level").load(id);
            for (DAOFinder<ExpStep> finder : finders.values()) {
                finder.add(player);
            }
            return player;
        }
        return player;
    }

    @Override
    public Collection<ExpStep> findAll() {
        return finders.get("level").findAll();
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
    public Collection<ExpStep> load() {
        if (load) {
            return null;
        }
        
        Collection<ExpStep> accs;
        if (preload) {
            accs = preloader.load();
        } else {
            accs = loaders.get("level").loadAll();
        }
        
        if (accs == null) {
            return new ArrayList<ExpStep>(0);
        }
        
        for (ExpStep acc : accs) {
            for (DAOFinder<ExpStep> f : finders.values()) {
                f.add(acc);
            }
        }
        
        load = true;
        return accs;
    }

    @Override
    public void unload() {
        preloader.unload();
        for (DAOFinder<ExpStep> f : finders.values()) {
            f.unload();
        }
        preload = false;
        load = false;
    }

    @Override
    public void setFinder(String a, DAOFinder<ExpStep> finder) {
        if (finder == null) {
            if (finders.containsKey(a)) {
                finders.remove(a);
            }
            return;
        }
        finders.put(a, finder);
    }

    @Override
    public DAOFinder<ExpStep> getFinder(String a) {
        return finders.get(a);
    }

    @Override
    public void setLoader(String a, DAOLoader<ExpStep> loader) {
        if (loader == null) {
            if (loaders.containsKey(a)) {
                loaders.remove(a);
            }
            return;
        }
        
        loaders.put(a, loader);
    }

    @Override
    public DAOLoader<ExpStep> getLoader(String a) {
        return loaders.get(a);
    }

    @Override
    public void setPreloader(DAOPreloader<ExpStep> loader) {
        preloader = loader;
    }

    @Override
    public DAOPreloader<ExpStep> getPreloader() {
        return preloader;
    }
}
