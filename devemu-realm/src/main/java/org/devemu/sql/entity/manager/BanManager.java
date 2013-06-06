<<<<<<< HEAD:DevemuRealm/src/org/devemu/sql/entity/manager/BanManager.java
package org.devemu.sql.entity.manager;

import java.util.ArrayList;
import java.util.List;
import org.devemu.sql.entity.Ban;

public class BanManager {
	public static List<String> getAllBan() {
		List<String> loc0 = new ArrayList<>();
		/*Session loc1 = Main.getSqlSession();
		Query loc2 = loc1.createQuery("from org.ishina.entity.Ban");
		for(Object loc3 : loc2.list()) {
			Ban loc4 = (Ban)loc3;
			loc0.add(loc4.getUsername());
		}*/
		return loc0;
	}
	
	public static long getBanTime(Ban arg0) {
		if(arg0.getBanTime() == 0)
			return 0;
		return (arg0.getBanTime() - System.currentTimeMillis());
	}
}
=======
package org.devemu.sql.entity.manager;

import java.util.ArrayList;
import java.util.List;
import org.devemu.sql.entity.Ban;

public class BanManager {
	public static List<String> getAllBan() {
		List<String> loc0 = new ArrayList<String>();
		/*Session loc1 = Main.getSqlSession();
		Query loc2 = loc1.createQuery("from org.ishina.entity.Ban");
		for(Object loc3 : loc2.list()) {
			Ban loc4 = (Ban)loc3;
			loc0.add(loc4.getUsername());
		}*/
		return loc0;
	}
	
	public static long getBanTime(Ban arg0) {
		if(arg0.getBanTime() == 0)
			return 0;
		return (arg0.getBanTime() - System.currentTimeMillis());
	}
}
>>>>>>> 800500b6eda7a3b433414a80b9c5ef278d93b04e:devemu-realm/src/main/java/org/devemu/sql/entity/manager/BanManager.java
