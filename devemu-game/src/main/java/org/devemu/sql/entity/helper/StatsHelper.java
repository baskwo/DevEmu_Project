package org.devemu.sql.entity.helper;

import org.devemu.file.FileProvider;
import org.devemu.sql.entity.Player;
import org.devemu.sql.entity.Stats;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class StatsHelper {
	@Inject @Named("stats") FileProvider sProvider;
	
	public Stats getBaseStat(Player p, int sId) {
		if(!p.getStats().containsKey(sId)) {
			return (Stats)sProvider.provide(p.getClasse(),sId);
		}
		return p.getStats().get(sId);
	}
}
