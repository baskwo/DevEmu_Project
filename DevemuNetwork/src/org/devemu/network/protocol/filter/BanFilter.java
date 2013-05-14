package org.devemu.network.protocol.filter;

import org.apache.mina.core.filterchain.IoFilterAdapter;

public class BanFilter extends IoFilterAdapter {
	//TODO: Finish BanFilter
	/*private List<String> banned = new ArrayList<String>();
	
	public BanFilter(List<String> arg0) {
		banned.addAll(arg0);
	}
	
	@Override
	public void messageReceived(NextFilter arg0, IoSession arg1, Object arg2) {
		String loc0 = arg2.toString();
		for(String loc1 : banned) {
			if(loc0.contains(loc1)) {
				arg1.close(true);
				break;
			}
		}
		arg0.messageReceived(arg1, arg2);
	}

	public List<String> getBanned() {
		return banned;
	}

	public void setBanned(List<String> banned) {
		this.banned = banned;
	}*/
}
