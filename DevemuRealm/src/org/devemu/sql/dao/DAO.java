package org.devemu.sql.dao;

import org.devemu.sql.dao.impl.IAccountDAO;
import org.devemu.sql.dao.impl.account.AccountDAO;

public class DAO {
    
    private static IAccountDAO accountDAO = new AccountDAO();

    private DAO() {
        
    }
    
    public static IAccountDAO getAccountDAO() {
        return accountDAO;
    }
    
    public static void setAccountDAO(IAccountDAO accountDAO) {
        DAO.accountDAO = accountDAO;
    }
}
