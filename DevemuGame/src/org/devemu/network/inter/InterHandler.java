package org.devemu.network.inter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.network.InterId;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.protocol.ServerPacket;

public class InterHandler extends IoHandlerAdapter{
	public void sessionCreated(IoSession session) throws Exception {
		session.setAttribute("client",new InterClient(session));
	}
	
	public void messageReceived(IoSession session, Object message) throws Exception {
		if(message instanceof IoBuffer) {
			ServerPacket loc1 = ServerPacket.decomp((IoBuffer)message);
			System.out.println("Receiving : " + InterId.getId((byte) loc1.getId()).name() + " from : " + session.getRemoteAddress());
			if(session.getAttribute("client") instanceof InterClient)
				((InterClient)session.getAttribute("client")).parse(loc1);
		}
	}
	
	public void messageSent(IoSession session, Object message) throws Exception {
		IoBuffer loc1 = (IoBuffer) message;
		int loc2 = loc1.get() >> 1;
		
		System.out.println("Sending : " + InterId.getId((byte) loc2).name() + " to : " + session.getRemoteAddress());
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		session.close(true);
	}
	
	public void exceptionCaught(IoSession session,Throwable cause) throws Exception {
		
	}
}
