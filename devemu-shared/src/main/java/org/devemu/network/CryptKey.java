package org.devemu.network;

import org.devemu.utils.Crypt;

public class CryptKey {
	private int id = 0;
	private String hash = "";
	private String key = "";
	
	public CryptKey() {
		hash = Crypt.randomIntHexString(32);
		prepareKey();
	}
	
	public void prepareKey() {
		for(int i = 0; i < (hash.length()); i += 2) {
			key += ((char) Integer.parseInt(hash.substring(i, i + 2),16));
		}
	}
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
