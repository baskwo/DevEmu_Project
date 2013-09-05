package org.devemu.network.server.client;

import org.devemu.network.client.SimpleClient;
import org.devemu.sql.entity.Account;
import org.devemu.sql.entity.helper.AccountHelper;
import org.devemu.sql.entity.helper.BanHelper;
import org.devemu.utils.Crypt;

import com.google.inject.Inject;

public class RealmClient extends SimpleClient{
	
	private String salt = "";
	private State state = State.CONNECT;
	private Account acc = null;
	private int queue = 0;
	private AccountHelper accHelper;
	private BanHelper banHelper;
	
	@Inject
	public RealmClient(AccountHelper aHelp, BanHelper bHelp) {
		accHelper = aHelp;
		banHelper = bHelp;
		salt = Crypt.randomString(32);
		getHash().setId(1);
	}
	
	public boolean isCrypt() {
		return getHash().getHash().length() > 0 ? true : false;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Account getAcc() {
		return acc;
	}

	public void setAcc(Account acc) {
		this.acc = acc;
	}

	public int getQueue() {
		return queue;
	}

	public void setQueue(int queue) {
		this.queue = queue;
	}

	public AccountHelper getAccHelper() {
		return accHelper;
	}

	public void setAccHelper(AccountHelper accHelper) {
		this.accHelper = accHelper;
	}

	public BanHelper getBanHelper() {
		return banHelper;
	}

	public void setBanHelper(BanHelper banHelper) {
		this.banHelper = banHelper;
	}
}
