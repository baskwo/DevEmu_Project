package org.devemu.network.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.devemu.network.protocol.Packet;
import org.devemu.network.server.client.RealmClient;
import org.devemu.network.server.client.RealmClient.State;

public class RealmHandler extends IoHandlerAdapter{
	
	public void sessionCreated(IoSession session) throws Exception{
		RealmClient loc1 = new RealmClient(session);
		session.setAttribute("client", loc1);
		
		Packet loc3 = new Packet();
		loc3.setIdentificator("HC");
		loc3.setFirstParam(loc1.getSalt());
		loc1.write(loc3.toString());
	}
	
	public void sessionClosed(IoSession session) throws Exception{
		session.close(true);
	}
	
	public void messageReceived(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		if(session.getAttribute("client") instanceof RealmClient) {
			RealmClient loc2 = (RealmClient)session.getAttribute("client");
			System.out.println("Receiving : " + loc1 + " from : " + session.getRemoteAddress().toString());
			if(loc2.getState() == State.SERVER) {
				Packet loc3 = Packet.decomp(loc1);
				loc2.parse(loc3);				
			}else{
				//loc1 = Packet.decompToString(loc1, loc2);
				loc2.parse(loc1);
			}
		}
	}
	
	public void messageSent(IoSession session, Object message) throws Exception{
		String loc1 = message.toString();
		System.out.println("Sending : " + loc1 + " to : " + session.getRemoteAddress().toString());
	}
}
