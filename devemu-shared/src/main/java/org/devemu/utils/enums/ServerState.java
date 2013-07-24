package org.devemu.utils.enums;

public enum ServerState {
	OFFLINE((byte)0),
    ONLINE((byte)1),
    SAVING((byte)2);
	
	private final byte state;
	
	private ServerState(byte state) {
		this.state = state;
	}
	
	public byte getState() {
		return this.state;
	}
	
	public static ServerState get(int state) {
		if(state == 1)
			return ONLINE;
        else if(state == 2)
        	return SAVING;
        else
        	return OFFLINE;
        
	}
}
