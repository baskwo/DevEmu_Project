package org.devemu.utils.enums;

public enum ServerState {
	OFFLINE((byte)0),
    ONLINE((byte)1),
    SAVING((byte)2);
	
	private final byte state;
	
	private ServerState(byte arg0) {
		state = arg0;
	}
	
	public byte getState() {
		return this.state;
	}
	
	public static ServerState get(int arg0) {
		if(arg0 == 1)
			return ONLINE;
        else if(arg0 == 2)
        	return SAVING;
        else
        	return OFFLINE;
        
	}
}
