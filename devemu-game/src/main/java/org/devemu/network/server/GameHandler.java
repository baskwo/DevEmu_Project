package org.devemu.network.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.network.protocol.Packet;
import org.devemu.network.server.client.GameClient;

public class GameHandler extends IoHandlerAdapter{
	
	public void sessionCreated(IoSession session) throws Exception{
		GameClient loc1 = new GameClient(session);
		session.setAttribute("client", loc1);
		
		Packet loc3 = new Packet();
		loc3.setIdentificator("HG");
		loc1.write(loc3.toString());
	}
	
	public void sessionClosed(IoSession session) throws Exception{
		session.close(true);
	}
	
	public void messageReceived(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		if(session.getAttribute("client") instanceof GameClient) {
			GameClient loc2 = (GameClient)session.getAttribute("client");
			System.out.println("Receiving : " + loc1 + " from : " + session.getRemoteAddress().toString());
			Packet loc3 = Packet.decomp(loc1);
			loc2.parse(loc3);
		}
	}
	
	public void messageSent(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		System.out.println("Sending : " + loc1 + " to : " + session.getRemoteAddress().toString());
	}
}
