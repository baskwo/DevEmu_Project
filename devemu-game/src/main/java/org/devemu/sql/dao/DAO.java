package org.devemu.sql.dao;

import org.devemu.program.Main;
import org.devemu.sql.Database;
import org.devemu.sql.dao.impl.IAccountDAO;
import org.devemu.sql.dao.impl.IBanDAO;
import org.devemu.sql.dao.impl.IExpDAO;
import org.devemu.sql.dao.impl.IMapsDAO;
import org.devemu.sql.dao.impl.IPlayerDAO;
import org.devemu.sql.dao.impl.account.AccountDAO;
import org.devemu.sql.dao.impl.ban.BanDAO;
import org.devemu.sql.dao.impl.exp.ExpDAO;
import org.devemu.sql.dao.impl.maps.MapsDAO;
import org.devemu.sql.dao.impl.player.PlayerDAO;
import org.devemu.utils.config.ConfigEnum;

public class DAO {
    
    private static IAccountDAO accountDAO = new AccountDAO();
    private static IPlayerDAO playerDAO = new PlayerDAO();
    private static IBanDAO banDAO = new BanDAO();
    private static IExpDAO expDAO = new ExpDAO();
    private static IMapsDAO mapsDAO = new MapsDAO();

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

	public static IPlayerDAO getPlayerDAO() {
		return playerDAO;
	}

	public static void setPlayerDAO(IPlayerDAO playerDAO) {
		DAO.playerDAO = playerDAO;
	}

	public static IBanDAO getBanDAO() {
		return banDAO;
	}

	public static void setBanDAO(IBanDAO banDAO) {
		DAO.banDAO = banDAO;
	}

	public static IExpDAO getExpDAO() {
		return expDAO;
	}

	public static void setExpDAO(IExpDAO expDAO) {
		DAO.expDAO = expDAO;
	}

	public static IMapsDAO getMapsDAO() {
		return mapsDAO;
	}

	public static void setMapsDAO(IMapsDAO mapsDAO) {
		DAO.mapsDAO = mapsDAO;
	}
}
