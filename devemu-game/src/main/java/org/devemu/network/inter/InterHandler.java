package org.devemu.network.inter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.network.inter.client.InterClient;
import org.devemu.program.Main;

public class InterHandler extends IoHandlerAdapter{
	public void sessionCreated(IoSession session) throws Exception {
		session.setAttribute("client",new InterClient(session));
	}
	
	public void messageReceived(IoSession session, Object message) throws Exception {
		if(message instanceof IoBuffer) {
			IoBuffer o = (IoBuffer)message;
			byte b = o.get();
			int id = b >> 1;
			boolean isAdmin = (b & 0x01) != 0;
			//TODO: Handle
		}
	}
	
	public void messageSent(IoSession session, Object message) throws Exception {
		IoBuffer loc1 = (IoBuffer) message;
		int loc2 = loc1.get();
		Main.log("Sending : " + loc2 + " to : " + session.getRemoteAddress(), InterHandler.class);
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		session.close(true);
	}
	
	public void exceptionCaught(IoSession session,Throwable cause) throws Exception {
		
	}
}
