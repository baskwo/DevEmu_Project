package org.devemu.sql.entity.helper;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.devemu.sql.entity.Account;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import static com.google.common.base.Throwables.propagate;

public class AccountHelper {
	Provider<EntityManager> emProvider;
	EntityManager em;
	
	@Inject
	public AccountHelper(Provider<EntityManager> em) {
		this.emProvider = em;
	}
	
	@Transactional
	public Account findByName(String name) {
		EntityManager em = getEntityManager();
		Account a = null;
		try {
			Query query = em.createNamedQuery("Account.findByName");
			query.setParameter("name", name);
			a = (Account) query.getSingleResult();
		} catch(Exception e) {
			throw propagate(e);
		} finally {
			em.flush();
		}
		return a;
	}
	
	private EntityManager getEntityManager() {
		if(em == null)
			em = emProvider.get();
		return em;
	}
	
	public long getAboTime(Account acc) {
		if(acc.getAboTime() == 0)
			return 0;
		return (acc.getAboTime() - System.currentTimeMillis());
	}
}
