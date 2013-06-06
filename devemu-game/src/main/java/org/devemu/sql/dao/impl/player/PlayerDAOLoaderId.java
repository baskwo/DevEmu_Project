package org.devemu.sql.dao.impl.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.devemu.program.Main;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.Database;
import org.devemu.sql.GetDatabase;
import org.devemu.sql.entity.Player;
import org.devemu.sql.manager.PlayerManager;
import org.devemu.utils.config.ConfigEnum;

public class PlayerDAOLoaderId implements DAOLoader<Player> {

    private Database database;
    private GetDatabase getDb = new GetDatabase() {
        @Override
        public Database getDatabase() {
            return Database.getDatabase((String) Main.getConfigValue(ConfigEnum.DATABASE_NAME));
        }
    };
    private boolean allowLoad = true;

    public PlayerDAOLoaderId() {
        
    }

    @Override
    public Player load(Object o) {
        if (!allowLoad) {
            return null;
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        Player acc = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM players WHERE guid = ?;");
            statement.setLong(1, (Long)o);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                acc = PlayerManager.create(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        return acc;
    }

    @Override
    public Collection<Player> loadAll() {
        if (!allowLoad) {
            return new ArrayList<Player>(0);
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        List<Player> players = new ArrayList<Player>(512);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM players;");
            while (resultSet.next()) {
                players.add(PlayerManager.create(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        /*if (!LoginConfig.getInstance().getConfiguration().getBoolean("Piou.Database.Players.CanLoadAfterStart")) {
            allowLoad = false;
        }*/
        return players;
    }

    @Override
    public void setGetDb(GetDatabase getDb) {
        this.getDb = getDb;
    }
    
}
