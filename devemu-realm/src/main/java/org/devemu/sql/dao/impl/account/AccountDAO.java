package org.devemu.sql.dao.impl.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.devemu.sql.DAOCreator;
import org.devemu.sql.DAOFinder;
import org.devemu.sql.DAOLoader;
import org.devemu.sql.DAOPreloader;
import org.devemu.sql.DAOUpdater;
import org.devemu.sql.dao.impl.IAccountDAO;
import org.devemu.sql.entity.Account;

public class AccountDAO implements IAccountDAO {

    private final Map<String, DAOFinder<Account>> finders = new HashMap<String, DAOFinder<Account>>(4);
    private final Map<String, DAOLoader<Account>> loaders = new HashMap<String, DAOLoader<Account>>(4);
    
    private DAOPreloader<Account> preloader = new AccountDAOPreloader();
    
    private boolean load;
    private boolean preload;
    
    private List<Account> toCreate = new ArrayList<Account>(8);
    private List<Account> toUpdate = new ArrayList<Account>(8);
    
    private DAOCreator<Account> creator;
    private DAOUpdater<Account> updater;

    public AccountDAO() {
        finders.put("id", new AccountDAOFinderId());
        finders.put("name", new AccountDAOFinderName());
        finders.put("pseudo", new AccountDAOFinderPseudo());
        
        loaders.put("id", new AccountDAOLoaderId());
        loaders.put("name", new AccountDAOLoaderName());
    }
    
    @Override
    public Account find(long id) {
        Account account = finders.get("id").find(id);
        if (account == null) {
            account = loaders.get("id").load(id);
            for (DAOFinder<Account> finder : finders.values()) {
                finder.add(account);
            }
            return account;
        }
        return account;
    }

    @Override
    public Account find(String name) {
        Account account = finders.get("name").find(name);
        if (account == null) {
            account = loaders.get("name").load(name);
            for (DAOFinder<Account> finder : finders.values()) {
                finder.add(account);
            }
            return account;
        }
        return account;
    }

    @Override
    public Account findBy(String a, Object o) {
        System.out.println(a);
        Account account = finders.get(a).find(o);
        if (account == null && loaders.containsKey(a)) {
            account = loaders.get(a).load(o);
            for (DAOFinder<Account> finder : finders.values()) {
                finder.add(account);
            }
            return account;
        }
        return account;
    }

    @Override
    public Collection<Account> findAll() {
        return finders.get("id").findAll();
    }

    @Override
    public void create(Account acc) {
        if (acc != null && acc.getGuid() == 0 && !toCreate.contains(acc)) {
            int id = 0;
            DAOFinder<Account> findById = finders.get("id");
            Collection<Account> accounts = findById.findAll();
            if (!accounts.isEmpty()) {
                id = Collections.max(findById.findAll(), new Comparator<Account>() {
                    @Override
                    public int compare(Account o1, Account o2) {
                        return o1.getGuid() - o2.getGuid() > 0 ? 1 : -1;
                    }
                }).getGuid() + 1;
            }
            acc.setGuid(id);
            toCreate.add(acc);
            for (DAOFinder<Account> finder : finders.values()) {
                finder.add(acc);
            }
        }
    }

    @Override
    public void update(Account acc) {
        if (acc != null && !toUpdate.contains(acc)) {
            toUpdate.add(acc);
        }
    }

    @Override
    public void commit() {
        List<Account> create = toCreate;
        if (!create.isEmpty()) {
            toCreate = new ArrayList<Account>(8);
            creator.create(create);
        }
        
        List<Account> update = toUpdate;
        if (!update.isEmpty()) {
            toUpdate = new ArrayList<Account>(8);
            updater.update(update);
        }
    }

    @Override
    public int preload() {
        if (load || preload) {
            return 0;
        }
        preload = true;
        return preloader.preload();
    }

    @Override
    public Collection<Account> load() {
        if (load) {
            return null;
        }
        
        Collection<Account> accs;
        if (preload) {
            accs = preloader.load();
        } else {
            accs = loaders.get("id").loadAll();
        }
        
        if (accs == null) {
            return new ArrayList<Account>(0);
        }
        
        for (Account acc : accs) {
            for (DAOFinder<Account> f : finders.values()) {
                f.add(acc);
            }
        }
        
        load = true;
        return accs;
    }

    @Override
    public void unload() {
        preloader.unload();
        for (DAOFinder<Account> f : finders.values()) {
            f.unload();
        }
        preload = false;
        load = false;
    }

    @Override
    public void setFinder(String a, DAOFinder<Account> finder) {
        if (finder == null) {
            if (finders.containsKey(a)) {
                finders.remove(a);
            }
            return;
        }
        finders.put(a, finder);
    }

    @Override
    public DAOFinder<Account> getFinder(String a) {
        return finders.get(a);
    }

    @Override
    public void setLoader(String a, DAOLoader<Account> loader) {
        if (loader == null) {
            if (loaders.containsKey(a)) {
                loaders.remove(a);
            }
            return;
        }
        
        loaders.put(a, loader);
    }

    @Override
    public DAOLoader<Account> getLoader(String a) {
        return loaders.get(a);
    }

    @Override
    public void setPreloader(DAOPreloader<Account> loader) {
        preloader = loader;
    }

    @Override
    public DAOPreloader<Account> getPreloader() {
        return preloader;
    }
    
}
