package org.devemu.network;

public enum InterId {
	CONNECT((byte)1),
	INFO((byte)2),
	INFO_AGREED((byte)3),
	ACC_WAITING((byte)4),
	ACC_WAITING_AGREED((byte)5);
	
	private byte id = 0;
	
	private InterId(byte id){
		setId(id);
	}
	
	public static InterId getId(byte id){
		if(id == 1) {
			return CONNECT;
		}
		if(id == 2) {
			return INFO;
		}
		if(id == 3) {
			return INFO_AGREED;
		}
		if(id == 4) {
			return ACC_WAITING;
		}
		if(id == 5) {
			return ACC_WAITING_AGREED;
		}
		return null;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}
}
