package org.devemu.sql.dao.impl.player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.devemu.program.Main;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.Database;
import org.devemu.sql.GetDatabase;
import org.devemu.sql.entity.Player;
import org.devemu.sql.manager.PlayerManager;
import org.devemu.utils.config.ConfigEnum;

public class PlayerDAOPreloader implements DAOPreloader<Player> {

    private List<String> preload;
    private GetDatabase getDb = new GetDatabase() {
        @Override
        public Database getDatabase() {
            return Database.getDatabase((String) Main.getConfigValue(ConfigEnum.DATABASE_NAME));
        }
    };
    private Database database;

    public PlayerDAOPreloader() {
        
    }
    
    @Override
    public int preload() {
        if (preload == null) {
            preload = new ArrayList<String>(512);
        }
        
        if (!preload.isEmpty()) {
            return preload.size();
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM players");
            while (resultSet.next()) {
                String s = StringUtils.join(new Object[]{';',
                        Long.toString(resultSet.getLong("id")), 
                        resultSet.getString("name"), 
                        resultSet.getString("password"), 
                        resultSet.getString("pseudo"),
                        Integer.toString(resultSet.getInt("rank")),
                        resultSet.getString("question"),
                        resultSet.getString("answer"),
                        Long.toString(resultSet.getLong("unbanDate")),
                        Long.toString(resultSet.getLong("suscribeEnd")),
                        Byte.toString(resultSet.getByte("community")),
                        resultSet.getString("characters"),
                        Byte.toString(resultSet.getByte("maxCharacters")),
                        Long.toString(resultSet.getLong("points"))});
                preload.add(s);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        
        return preload.size();
    }

    @Override
    public Collection<Player> load() {
        if (preload == null) {
            return new ArrayList<Player>(0);
        }
        List<Player> players = new ArrayList<Player>(preload.size());
        for (String s : preload) {
            players.add(PlayerManager.create(s.split(";")));
        }
        preload.clear();
        return players;
    }

    @Override
    public void unload() {
        if (preload == null || preload.isEmpty()) {
            return;
        }
        preload.clear();
    }

    @Override
    public void setGetDb(GetDatabase getDb) {
        this.getDb = getDb;
    }
    
}
