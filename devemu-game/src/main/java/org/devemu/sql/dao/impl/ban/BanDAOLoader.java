package org.devemu.sql.dao.impl.ban;

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
import org.devemu.sql.entity.Ban;
import org.devemu.sql.manager.BanManager;
import org.devemu.utils.config.ConfigEnum;

public class BanDAOLoader implements DAOLoader<Ban> {

    private Database database;
    private GetDatabase getDb = new GetDatabase() {
        @Override
        public Database getDatabase() {
            return Database.getDatabase((String) Main.getConfigValue(ConfigEnum.DATABASE_NAME));
        }
    };
    private boolean allowLoad = true;

    public BanDAOLoader() {
        
    }

    @Override
    public Ban load(Object o) {
        if (!allowLoad) {
            return null;
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        Ban acc = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM players WHERE id = ?;");
            statement.setLong(1, (Long)o);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                acc = BanManager.create(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        return acc;
    }

    @Override
    public Collection<Ban> loadAll() {
        if (!allowLoad) {
            return new ArrayList<Ban>(0);
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        List<Ban> players = new ArrayList<Ban>(512);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM players;");
            while (resultSet.next()) {
                players.add(BanManager.create(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        /*if (!LoginConfig.getInstance().getConfiguration().getBoolean("Piou.Database.Bans.CanLoadAfterStart")) {
            allowLoad = false;
        }*/
        return players;
    }

    @Override
    public void setGetDb(GetDatabase getDb) {
        this.getDb = getDb;
    }
    
}
