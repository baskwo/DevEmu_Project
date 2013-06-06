package org.devemu.sql.dao.impl.account;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.entity.Account;

public class AccountDAOFinderId implements DAOFinder<Account> {
    
    private final Map<Long, Account> accounts = new HashMap<Long, Account>(512);
    private final Collection<Account> synchronizedAccounts = Collections.synchronizedCollection(Collections.unmodifiableCollection(accounts.values()));

    @Override
    public Account find(Object o) {
        if (o instanceof Long) {
            return accounts.get(o);
        }
        throw new IllegalArgumentException("AccountDAOFinderId.find(Object o) : o must be a Long.");
    }

    @Override
    public Collection<Account> findAll() {
        return synchronizedAccounts;
    }

    @Override
    public Account add(Account account) {
        if (account == null) {
            return null;
        }
        
        long id = account.getGuid();
        if (!accounts.containsKey(id)) {
            accounts.put(id, account);
        }
        return account;
    }

    @Override
    public void remove(Account account) {
        if (account == null) {
            return;
        }
        
        long id = account.getGuid();
        if (accounts.containsKey(id)) {
            accounts.remove(id);
        }
    }

    @Override
    public void unload() {
        accounts.clear();
    }
    
}
