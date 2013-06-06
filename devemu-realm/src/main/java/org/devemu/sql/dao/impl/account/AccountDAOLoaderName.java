package org.devemu.sql.dao.impl.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.devemu.program.Main;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.Database;
import org.devemu.sql.GetDatabase;
import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.manager.AccountManager;
import org.devemu.utils.config.ConfigEnum;

public class AccountDAOLoaderName implements DAOLoader<Account> {

    private Database database;
    private GetDatabase getDb = new GetDatabase() {
        @Override
        public Database getDatabase() {
            return Database.getDatabase((String) Main.getConfigValue(ConfigEnum.DATABASE_NAME));
        }
    };
    private boolean allowLoad = true;

    public AccountDAOLoaderName() {
        /*if (!LoginConfig.getInstance().getConfiguration().getBoolean("Piou.Database.Accounts.CanLoadAfterStart")) {
            allowLoad = false;
        }*/
    }

    @Override
    public Account load(Object o) {
        if (!allowLoad) {
            return null;
        }
        
        if (database == null) {
            database = getDb.getDatabase();
        }
        
        Account acc = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM accounts WHERE name = ?;");
            statement.setString(1, (String)o);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                acc = AccountManager.create(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.close(resultSet, statement, connection);
        }
        return acc;
    }

    @Override
    public Collection<Account> loadAll() {
        return null;
    }

    @Override
    public void setGetDb(GetDatabase getDb) {
        this.getDb = getDb;
    }
        
}
