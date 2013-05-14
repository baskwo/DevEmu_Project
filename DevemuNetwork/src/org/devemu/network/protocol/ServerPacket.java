package org.devemu.network.protocol;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

public class ServerPacket {
	private int id = 0;
	private boolean isCrypt = false;
	private IoBuffer data = IoBuffer.allocate(1024);
	
	public static ServerPacket decomp(IoBuffer arg0) {
		ServerPacket loc2 = new ServerPacket();
		byte loc1 = arg0.get();
		loc2.id = loc1 >> 1;
		loc2.isCrypt = (loc1 & 0x01) != 0;
		loc2.data = IoBuffer.allocate(arg0.remaining());
		loc2.data.put(arg0);
		loc2.data.flip();
		return loc2;
	}
	
	public IoBuffer toBuff() {
		data.flip();
		IoBuffer loc1 = IoBuffer.allocate(data.capacity() + 1);
		byte loc2 = (byte) (id << 1 | (isCrypt ? 1 : 0));
		loc1.put(loc2);
		loc1.put(data);
		loc1.flip();
		return loc1;
	}
	
	public void writeString(String arg0) {
		try {
			data.putPrefixedString(arg0, Charset.forName("ISO-8859-1").newEncoder());
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
	}
	
	public String readString() {
		String loc1 = "";
		try {
			loc1 = data.getPrefixedString(Charset.forName("ISO-8859-1").newDecoder());
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		return loc1;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isCrypt() {
		return isCrypt;
	}
	public void setCrypt(boolean isCrypt) {
		this.isCrypt = isCrypt;
	}
	public IoBuffer getData() {
		return data;
	}
	public void setData(IoBuffer data) {
		this.data = data;
	}
}
