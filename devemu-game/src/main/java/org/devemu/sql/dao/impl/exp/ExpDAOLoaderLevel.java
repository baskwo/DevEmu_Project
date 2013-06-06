<<<<<<< HEAD:DevemuGame/src/org/devemu/sql/dao/impl/exp/ExpDAOLoaderLevel.java
package org.devemu.sql.dao.impl.exp;

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
import org.devemu.sql.entity.ExpStep;
import org.devemu.sql.manager.ExpManager;
import org.devemu.utils.config.ConfigEnum;

public class ExpDAOLoaderLevel implements DAOLoader<ExpStep> {

    private Database database;
    private GetDatabase getDb = new GetDatabase() {
        @Override
        public Database getDatabase() {
            return Database.getDatabase((String) Main.getConfigValue(ConfigEnum.DATABASE_NAME));
        }
    };
    private boolean allowLoad = true;

    public ExpDAOLoaderLevel() {
        
    }

    @Override
    public ExpStep load(Object o) {
        if (!allowLoad) {
            return null;
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        ExpStep acc = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM expsteps WHERE level = ?;");
            statement.setLong(1, (Long)o);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                acc = ExpManager.create(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        return acc;
    }

    @Override
    public Collection<ExpStep> loadAll() {
        if (!allowLoad) {
            return new ArrayList<>(0);
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        List<ExpStep> players = new ArrayList<ExpStep>(512);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM players;");
            while (resultSet.next()) {
                players.add(ExpManager.create(resultSet));
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
=======
package org.devemu.sql.dao.impl.exp;

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
import org.devemu.sql.entity.ExpStep;
import org.devemu.sql.manager.ExpManager;
import org.devemu.utils.config.ConfigEnum;

public class ExpDAOLoaderLevel implements DAOLoader<ExpStep> {

    private Database database;
    private GetDatabase getDb = new GetDatabase() {
        @Override
        public Database getDatabase() {
            return Database.getDatabase((String) Main.getConfigValue(ConfigEnum.DATABASE_NAME));
        }
    };
    private boolean allowLoad = true;

    public ExpDAOLoaderLevel() {
        
    }

    @Override
    public ExpStep load(Object o) {
        if (!allowLoad) {
            return null;
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        ExpStep acc = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM expsteps WHERE level = ?;");
            statement.setLong(1, (Long)o);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                acc = ExpManager.create(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        return acc;
    }

    @Override
    public Collection<ExpStep> loadAll() {
        if (!allowLoad) {
            return new ArrayList<ExpStep>(0);
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        List<ExpStep> players = new ArrayList<ExpStep>(512);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM players;");
            while (resultSet.next()) {
                players.add(ExpManager.create(resultSet));
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
>>>>>>> 800500b6eda7a3b433414a80b9c5ef278d93b04e:devemu-game/src/main/java/org/devemu/sql/dao/impl/exp/ExpDAOLoaderLevel.java
