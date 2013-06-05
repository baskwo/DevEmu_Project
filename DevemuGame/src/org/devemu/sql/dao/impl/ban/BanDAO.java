package org.devemu.sql.dao.impl.ban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.devemu.program.Main;
import org.devemu.sql.DAOCreator;
import org.devemu.sql.DAODeleter;
import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.DAOUpdater;
import org.devemu.sql.Database;
import org.devemu.sql.dao.impl.IBanDAO;
import org.devemu.sql.dao.impl.IPlayerDAO;
import org.devemu.sql.entity.Player;
import org.devemu.utils.config.ConfigEnum;

public class BanDAO implements IBanDAO {

    private final Map<String, DAOFinder<Player>> finders = new HashMap<String, DAOFinder<Player>>(4);
    private final Map<String, DAOLoader<Player>> loaders = new HashMap<String, DAOLoader<Player>>(4);
    
    private DAOPreloader<Player> preloader = new PlayerDAOPreloader();
    
    private boolean load;
    private boolean preload;
    
    private List<Player> toCreate = new ArrayList<Player>(8);
    private List<Player> toUpdate = new ArrayList<Player>(8);
    private List<Player> toDelete = new ArrayList<Player>(8);
    
    private DAOCreator<Player> creator;
    private DAOUpdater<Player> updater;
    private DAODeleter<Player> deleter;

    public BanDAO() {
        finders.put("id", new PlayerDAOFinder());
        
        loaders.put("id", new PlayerDAOLoader());
    }
    
    @Override
    public Player find(long id) {
        Player player = finders.get("id").find(id);
        if (player == null) {
            player = loaders.get("id").load(id);
            for (DAOFinder<Player> finder : finders.values()) {
                finder.add(player);
            }
            return player;
        }
        return player;
    }

    @Override
    public Player find(String name) {
        Player player = finders.get("name").find(name);
        if (player == null) {
            player = loaders.get("name").load(name);
            for (DAOFinder<Player> finder : finders.values()) {
                finder.add(player);
            }
            return player;
        }
        return player;
    }

    @Override
    public Player findBy(String a, Object o) {
        System.out.println(a);
        Player player = finders.get(a).find(o);
        if (player == null && loaders.containsKey(a)) {
            player = loaders.get(a).load(o);
            for (DAOFinder<Player> finder : finders.values()) {
                finder.add(player);
            }
            return player;
        }
        return player;
    }

    @Override
    public Collection<Player> findAll() {
        return finders.get("id").findAll();
    }
    
    @Override
	public int getNextID() {
    	Database db = Database.getDatabase((String) Main.getConfigValue(ConfigEnum.DATABASE_NAME));
    	int nextId = 0;
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement("SELECT MAX(id) FROM players;");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nextId = resultSet.getInt("MAX(id)");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
		return nextId;
	}

    @Override
    public void create(Player acc) {
        if (acc != null && acc.getGuid() == 0 && !toCreate.contains(acc)) {
            int id = 0;
            DAOFinder<Player> findById = finders.get("id");
            Collection<Player> players = findById.findAll();
            if (!players.isEmpty()) {
                id = Collections.max(findById.findAll(), new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        return o1.getGuid() - o2.getGuid() > 0 ? 1 : -1;
                    }
                }).getGuid() + 1;
            }
            acc.setGuid(id);
            toCreate.add(acc);
            for (DAOFinder<Player> finder : finders.values()) {
                finder.add(acc);
            }
        }
    }

    @Override
    public void update(Player acc) {
        if (acc != null && !toUpdate.contains(acc)) {
            toUpdate.add(acc);
        }
    }
    
    @Override
	public void delete(Player acc) {
    	if(acc != null && !toDelete.contains(acc))
    		toDelete.add(acc);
	}
    
    @Override
    public void commit() {
        List<Player> create = toCreate;
        if (!create.isEmpty()) {
            toCreate = new ArrayList<Player>(8);
            creator.create(create);
        }
        
        List<Player> update = toUpdate;
        if (!update.isEmpty()) {
            toUpdate = new ArrayList<Player>(8);
            updater.update(update);
        }
        
        List<Player> delete = toDelete;
        if(!delete.isEmpty()) {
        	toDelete = new ArrayList<Player>(8);
        	deleter.delete(delete);
        }
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
    public Collection<Player> load() {
        if (load) {
            return null;
        }
        
        Collection<Player> accs;
        if (preload) {
            accs = preloader.load();
        } else {
            accs = loaders.get("id").loadAll();
        }
        
        if (accs == null) {
            return new ArrayList<Player>(0);
        }
        
        for (Player acc : accs) {
            for (DAOFinder<Player> f : finders.values()) {
                f.add(acc);
            }
        }
        
        load = true;
        return accs;
    }

    @Override
    public void unload() {
        preloader.unload();
        for (DAOFinder<Player> f : finders.values()) {
            f.unload();
        }
        preload = false;
        load = false;
    }

    @Override
    public void setFinder(String a, DAOFinder<Player> finder) {
        if (finder == null) {
            if (finders.containsKey(a)) {
                finders.remove(a);
            }
            return;
        }
        finders.put(a, finder);
    }

    @Override
    public DAOFinder<Player> getFinder(String a) {
        return finders.get(a);
    }

    @Override
    public void setLoader(String a, DAOLoader<Player> loader) {
        if (loader == null) {
            if (loaders.containsKey(a)) {
                loaders.remove(a);
            }
            return;
        }
        
        loaders.put(a, loader);
    }

    @Override
    public DAOLoader<Player> getLoader(String a) {
        return loaders.get(a);
    }

    @Override
    public void setPreloader(DAOPreloader<Player> loader) {
        preloader = loader;
    }

    @Override
    public DAOPreloader<Player> getPreloader() {
        return preloader;
    }

	public DAODeleter<Player> getDeleter() {
		return deleter;
	}

	public void setDeleter(DAODeleter<Player> deleter) {
		this.deleter = deleter;
	}

	public List<Player> getToDelete() {
		return toDelete;
	}

	public void setToDelete(List<Player> toDelete) {
		this.toDelete = toDelete;
	}

}
