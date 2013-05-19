package org.devemu.sql.dao.impl.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.devemu.program.Main;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.Database;
import org.devemu.sql.GetDatabase;
import org.devemu.sql.entity.Player;
import org.devemu.sql.manager.PlayerManager;
import org.devemu.utils.config.ConfigEnum;

public class PlayerDAOLoaderName implements DAOLoader<Player> {

    private Database database;
    private GetDatabase getDb = new GetDatabase() {
        @Override
        public Database getDatabase() {
            return Database.getDatabase((String) Main.getConfigValue(ConfigEnum.DATABASE_NAME));
        }
    };
    private boolean allowLoad = true;

    public PlayerDAOLoaderName() {
        /*if (!LoginConfig.getInstance().getConfiguration().getBoolean("Piou.Database.Players.CanLoadAfterStart")) {
            allowLoad = false;
        }*/
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
            statement = connection.prepareStatement("SELECT * FROM players WHERE name = ?;");
            statement.setString(1, (String)o);
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
        return null;
    }

    @Override
    public void setGetDb(GetDatabase getDb) {
        this.getDb = getDb;
    }
        
}
