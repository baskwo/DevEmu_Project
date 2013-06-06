package org.devemu.sql.dao.impl.account;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.entity.Account;

public class AccountDAOFinderName implements DAOFinder<Account> {
    
    private final Map<String, Account> accounts = new HashMap<String, Account>(512);
    private final Collection<Account> synchronizedAccounts = Collections.synchronizedCollection(Collections.unmodifiableCollection(accounts.values()));

    @Override
    public Account find(Object o) {
        if (o instanceof String) {
            return accounts.get(o.toString());
        }
        throw new IllegalArgumentException("AccountDAOFinderName.find(Object o) : o must be a String.");
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
        
        String name = account.getName();
        if (!accounts.containsKey(name)) {
            accounts.put(name, account);
        }
        return account;
    }

    @Override
    public void remove(Account account) {
        if (account == null) {
            return;
        }
        
        String name = account.getName();
        if (accounts.containsKey(name)) {
            accounts.remove(name);
        }
    }

    @Override
    public void unload() {
        accounts.clear();
    }
    
}
