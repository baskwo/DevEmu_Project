package org.devemu.network.message;

@SuppressWarnings("serial")
public class MessageNotFoundException extends Exception{

	public MessageNotFoundException() { super(); }
	public MessageNotFoundException(String message) { super(message); }
	public MessageNotFoundException(String message, Throwable cause) { super(message, cause); }
	public MessageNotFoundException(Throwable cause) { super(cause); }
}
