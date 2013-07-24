package org.devemu.network.message;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

public abstract class InterMessage {
	public abstract void serialize();
	public abstract void deserialize();
	
	public IoBuffer in = IoBuffer.allocate(1024);
	public IoBuffer out = IoBuffer.allocate(1024);
	
	public IoBuffer getIn() {
		return in;
	}

	public void setIn(IoBuffer in) {
		this.in = in;
	}

	public IoBuffer getOut() {
		return out;
	}

	public void setOut(IoBuffer out) {
		this.out = out;
	}

	public void writeString(String arg0) {
		try {
			out.putPrefixedString(arg0, Charset.forName("ISO-8859-1").newEncoder());
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
	}
	
	public String readString() {
		String out = "";
		try {
			out = in.getPrefixedString(Charset.forName("ISO-8859-1").newDecoder());
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		return out;
	}
}