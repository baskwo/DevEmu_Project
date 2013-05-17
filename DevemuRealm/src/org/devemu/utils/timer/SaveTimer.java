package org.devemu.utils.timer;

import org.devemu.sql.dao.DAO;

public class SaveTimer implements Runnable{

	@Override
	public void run() {
		DAO.getAccountDAO().commit();
	}

}
