package org.devemu.network.server.message.inter;

import org.devemu.network.message.InterMessage;
import org.devemu.network.message.InterPacket;
import org.devemu.program.Main;

@InterPacket(id = "1")
public class ConnectionInterMessage extends InterMessage {

	@Override
	public void serialize() {
		out.put((byte) 1);
		out.put((byte) Main.getGuid());
		out.put(Main.getState().getState());
		out.put(Main.getPopulation().getPopulation());
		writeString(Main.getConfigValue("devemu.service.game.addr"));
		out.putInt(Integer.parseInt(Main.getConfigValue("devemu.service.game.port")));
		out.put((byte) (Main.isAllowNoSubscribe() ? 1 : 0));
		out.flip();
	}

	@Override
	public void deserialize() {}
}
