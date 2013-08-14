package org.devemu.file.provider;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.devemu.file.FileProvider;
import org.devemu.sql.entity.Stats;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.io.Files;

import static com.google.common.base.Throwables.propagate;

public class StatsFileProvider extends FileProvider {
	ImmutableTable<Integer, Integer, Integer> baseStats;
	
	public static StatsFileProvider from(String file) {
		StatsFileProvider provider = new StatsFileProvider();
		provider.init(file);
		return provider;
	}
	
	public void init(String file) {
		Table<Integer, Integer, Integer> stats = HashBasedTable.create();
		try {
			List<String> lines = Files.readLines(new File(file), Charset.forName("ISO-8859-1"));
			for(String s : lines) {
				String[] args = s.split(",");
				if(args.length == 3) {
					int classe = Integer.parseInt(args[0]);
					int statsId = Integer.parseInt(args[1]);
					int value = Integer.parseInt(args[2]);
					stats.put(classe, statsId, value);
				}
			}
			baseStats = ImmutableTable.copyOf(stats);
		} catch (IOException e) {
			throw propagate(e);
		}
	}

	@Override
	public Object provide(Object... objects) {
		Stats stats = new Stats();
		if(objects.length == 2 && objects[0] instanceof Integer && objects[1] instanceof Integer) {
			if(baseStats.contains(objects[0], objects[1])) {
				
				stats.setStatsId((int)objects[0]);
				stats.setValue((int)objects[1]);
				return stats;
			}
		}
		stats.setStatsId((int)objects[0]);
		stats.setValue(0);
		return stats;
	}
}
