package org.devemu.sql.entity.helper;

import java.util.ArrayList;
import java.util.List;

import org.devemu.program.Main;
import org.devemu.sql.entity.Player;

import com.google.common.base.Joiner;

public class PlayerHelper {
	public String toALK(Player p) {
		String out = "";
		//guid;name;level;gfx;colors0;colors1;colors2;stuffALK;isSeller;gameId;isDead;DeathCount;levelMax;
		out += (p.getGuid() + ";"
				+ p.getName() + ";"
				+ p.getLevel() + ";"
				+ (p.getGfx() == -1 ? getDefaultGfx(p) : p.getGfx()) + ";");
		for(int i = 0; i < 3; i++) {
			int color = p.getColor(i);
			out += ((color != -1 ? Integer.toHexString(color) : "-1") + ";");
		}
		out += ((itemToALK(p)) + ";"
				+ (p.isMarchant() ? "1" : "0") + ";"
				+ ("" + Main.getGuid()) + ";"
				+ (p.isDead() ? "1" : "0") + ";"
				+ ("" + p.getCountDead()) + ";");
				//+ ExpManager.getCount() + ";");
		return out;
	}
	
	public String itemToALK(Player p) {
		String out = "";
		//TODO: When item will be more advance
		return out;
	}
	
	public String toASK(Player p) {
		List<String> out = new ArrayList<String>();
		//guid|name|level|classe|sexe|gfxid|color1|color2|color3|itemToASK
		out.add("" + p.getGuid());
		out.add(p.getName());
		out.add("" + p.getLevel());
		out.add(p.isSexe() ? "1" : "0");
		out.add(p.getGfx() == -1 ? getDefaultGfx(p) : ("" + p.getGfx()));
		for(int i = 0; i < 3; i++) {
			int color = p.getColor(i);
			out.add(color != -1 ? Integer.toHexString(color) : "-1");
		}
		out.add(itemToASK(p));
		return Joiner.on("|").join(out.toArray());
	}
	
	public String itemToASK(Player p) {
		String out = "";
		//TODO: When item will be more advance
		return out;
	}
	
	public String getXpToString(Player p) {
		String out = "";
		/*ExpStep loc1 = ExpManager.getExpStepByLevel(arg0.getLevel());
		ExpStep loc2 = ExpManager.getExpStepByLevel(arg0.getLevel() + 1);
		loc0 += arg0.getXp() + "," + loc1.getPlayer() + "," + loc2.getPlayer();*/
		return out;
	}
	
	public List<String> getAsData(Player p) {
		List<String> out = new ArrayList<String>();
		out.add("" + p.getKamas());
		out.add("" + p.getCapitals());
		out.add("" + p.getSpellPoints());
		out.add(p.getAlign().getOrdre() + "~" + p.getAlign().getOrdre() + "," +
				p.getAlign().getLevel() + "," + p.getAlign().getGrade() +
				p.getAlign().getHonor() + "," + p.getAlign().getDeshonor() + (p.isShowingWings() ? "1" : "0"));
		out.add(p.getPdv() + "," + getPdvMax(p));
		return out;
	}
	
	public int getPdvMax(Player p) {
		int lifeByPerso = 5;
		if(p.getClasse() == 1)
			lifeByPerso = 10;
		return 50 + (lifeByPerso * p.getLevel());
		//return 50 + (lifeByPerso * arg0.getLevel()) + arg0.getStats().get(StatsID.STATS_ADD_VITA).getValue();
	}
	
	public String getDefaultGfx(Player p) {
		return ("" + p.getClasse() + (p.isSexe() ? "1" : "0")); 
	}
	
	public String getGMData(Player p) {
		List<Object> out = new ArrayList<>();
		out.add(p.getCell());
		out.add(p.getOrientation());
		out.add(0);//TODO:Found what it is
		out.add(p.getGuid());
		out.add(p.getName());
		out.add(p.getClasse());
		out.add("");//TODO: Title
		out.add(p.getGfx() + "^" + p.getSize());
		out.add(p.isSexe() ? 1 : 0);
		out.add(p.getAlign().getOrdre() + "," + "0"+ "," + p.getAlign().getGrade() + "," +
				(p.getLevel() + p.getGuid()) + "," + (p.getAlign().getDeshonor() > 0 ? 1 : 0));
		out.add(p.getColor(0) == -1 ? -1 : Integer.toHexString(p.getColor(0)));
		out.add(p.getColor(1) == -1 ? -1 : Integer.toHexString(p.getColor(1)));
		out.add(p.getColor(2) == -1 ? -1 : Integer.toHexString(p.getColor(2)));
		out.add("");//GMStuff
		out.add(p.getLevel() > 99 ? (p.getLevel() > 199 ? 2 : 1) : 0);
		out.add("");//Emote
		out.add("");//EmoteTimer
		out.add("");//GuildName
		out.add("");//GuildEmblem
		out.add("");//Speed
		out.add("");//DragoColor
		out.add("");//??
		
		return Joiner.on(";").join(out.toArray());
	}
}
