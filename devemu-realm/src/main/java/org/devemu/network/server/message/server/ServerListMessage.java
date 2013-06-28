package org.devemu.network.server.message.server;

import org.devemu.network.client.BaseClient.State;
import org.devemu.network.message.Message;
import org.devemu.network.message.Packet;

import com.google.common.collect.Multiset;

@Packet(id="Ax",state = State.SERVER)
public class ServerListMessage extends Message {
	public Multiset<Integer> list;
	public long aboTime;

	@Override
	public void serialize() {
		output = "AxK" + aboTime + "|";
		for(Multiset.Entry<Integer> loc7 : list.entrySet()) {
			output += (loc7.getElement() + "," + loc7.getCount() + "|");
		}
	}

	@Override
	public void deserialize() {
	}
}
