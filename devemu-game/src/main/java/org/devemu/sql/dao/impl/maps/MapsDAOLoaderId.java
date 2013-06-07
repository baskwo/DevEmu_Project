package org.devemu.sql.dao.impl.maps;

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
import org.devemu.sql.entity.Maps;
import org.devemu.sql.manager.MapsManager;
import org.devemu.utils.config.ConfigEnum;

public class MapsDAOLoaderId implements DAOLoader<Maps> {

    private Database database;
    private GetDatabase getDb = new GetDatabase() {
        @Override
        public Database getDatabase() {
            return Database.getDatabase((String) Main.getConfigValue(ConfigEnum.DATABASE_NAME));
        }
    };
    private boolean allowLoad = true;

    public MapsDAOLoaderId() {
        
    }

    @Override
    public Maps load(Object o) {
        if (!allowLoad) {
            return null;
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        Maps acc = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM maps WHERE id = ?;");
            statement.setLong(1, (Long)o);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                acc = MapsManager.create(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        return acc;
    }

    @Override
    public Collection<Maps> loadAll() {
        if (!allowLoad) {
            return new ArrayList<Maps>(0);
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        List<Maps> maps = new ArrayList<Maps>(512);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM maps;");
            while (resultSet.next()) {
                maps.add(MapsManager.create(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        /*if (!LoginConfig.getInstance().getConfiguration().getBoolean("Piou.Database.Players.CanLoadAfterStart")) {
            allowLoad = false;
        }*/
        return maps;
    }

    @Override
    public void setGetDb(GetDatabase getDb) {
        this.getDb = getDb;
    }
    
}
