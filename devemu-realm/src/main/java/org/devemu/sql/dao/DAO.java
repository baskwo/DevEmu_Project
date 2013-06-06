package org.devemu.sql.dao;

import org.devemu.program.Main;
import org.devemu.sql.Database;
import org.devemu.sql.dao.impl.IAccountDAO;
import org.devemu.sql.dao.impl.account.AccountDAO;
import org.devemu.utils.config.ConfigEnum;

public class DAO {
    
    private static IAccountDAO accountDAO = new AccountDAO();

    private DAO() {
        
    }
    
    public static void init() {
    	String loc1 = (String) Main.getConfigValue(ConfigEnum.DATABASE_NAME);
    	String loc2 = (String) Main.getConfigValue(ConfigEnum.DATABASE_DRIVER);
    	String loc3 = Main.getConfigValue(ConfigEnum.DATABASE_URL) + loc1;
    	String loc4 = (String) Main.getConfigValue(ConfigEnum.DATABASE_USER);
    	String loc5 = (String) Main.getConfigValue(ConfigEnum.DATABASE_USER_PASS);
    	Database.newDatabase(loc1,loc2,loc3, loc4, loc5, 10, 500, false);
    }
    
    public static IAccountDAO getAccountDAO() {
        return accountDAO;
    }
    
    public static void setAccountDAO(IAccountDAO accountDAO) {
        DAO.accountDAO = accountDAO;
    }
}
