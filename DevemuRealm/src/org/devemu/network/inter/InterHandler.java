package org.devemu.network.inter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.network.InterId;
import org.devemu.network.inter.client.InterClient;
import org.devemu.network.inter.client.InterManager;
import org.devemu.network.protocol.ServerPacket;
import org.devemu.network.server.client.ClientManager;
import org.devemu.utils.enums.ServerState;

public class InterHandler extends IoHandlerAdapter{
	@Override
	public void sessionCreated(IoSession session) throws Exception{
		ServerPacket loc1 = new ServerPacket();
		loc1.setId(InterId.CONNECT.getId());
		session.write(loc1.toBuff());
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception{
		if(message instanceof IoBuffer) {
			ServerPacket loc1 = ServerPacket.decomp((IoBuffer)message);
			System.out.println("Receiving : " + InterId.getId((byte) loc1.getId()).name() + " from : " + session.getRemoteAddress());
			if(session.containsAttribute("client")) {
				if(session.getAttribute("client") instanceof InterClient)
					((InterClient)session.getAttribute("client")).parse(loc1);
			}else{
				InterManager.onInfo(session, loc1);
			}
		}
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception{
		IoBuffer loc1 = (IoBuffer) message;
		int loc2 = loc1.get() >> 1;
		
		System.out.println("Sending : " + InterId.getId((byte) loc2).name() + " to : " + session.getRemoteAddress());
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception{
		if(session.getAttribute("client") instanceof InterClient) {
			((InterClient)session.getAttribute("client")).setState(ServerState.OFFLINE);
			ClientManager.refreshServer();
		}
		session.close(true);
	}
	
	@Override
	public void exceptionCaught(IoSession session,Throwable cause) throws Exception {
		
	}
}
