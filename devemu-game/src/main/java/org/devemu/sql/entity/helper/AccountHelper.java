package org.devemu.sql.entity.helper;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.Player;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import static com.google.common.base.Throwables.propagate;

public class AccountHelper {
	Provider<EntityManager> emProvider;
	EntityManager em;
	
	@Inject
	public AccountHelper(Provider<EntityManager> emProvider) {
		this.emProvider = emProvider;
	}
	
	@Transactional
	public Account findById(int id) {
		EntityManager em = getEntityManager();
		Account a = null;
		try {
			Query query = em.createNamedQuery("Account.findById");
			query.setParameter("id", id);
			a = (Account) query.getSingleResult();
		} catch(Exception e) {
			throw propagate(e);
		}finally{
			em.flush();
		}
		return a;
	}
	
	public long getAboTime(Account acc) {
		if(acc.getAboTime() == 0)
			return 0;
		return (acc.getAboTime() - System.currentTimeMillis());
	}
	
	@Transactional
	public boolean removePlayer(int pId, Account acc) {
		EntityManager em = getEntityManager();
		boolean isDeleted = false;
		if(acc.getPlayers().contains(pId)) {
			for(int i = 0; i < acc.getPlayers().size(); i++) {
				if(acc.getPlayers().get(i).getGuid() == pId) {
					try {
						em.remove(acc.getPlayers().get(i));
						em.refresh(acc);
					}catch(Exception e) {
						throw propagate(e);
					}finally{
						isDeleted = true;
						em.close();
					}
					acc.getPlayers().remove(i);
					break;
				}
			}
		}
		return isDeleted;
	}
	
	private EntityManager getEntityManager() {
		if(em == null)
			em = emProvider.get();
		return em;
	}
	
	public Player getPlayer(int pId, Account acc) {
		for(Player p : acc.getPlayers()) {
			if(p.getGuid() == pId)
				return p;
		}
		return null;
	}
}
