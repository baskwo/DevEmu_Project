package org.devemu.sql.manager;

import java.util.ArrayList;
import java.util.List;

import org.devemu.program.Main;
import org.devemu.sql.entity.Player;
import org.devemu.sql.entity.Stats;
import org.devemu.utils.constants.StatsID;

import com.google.common.base.Joiner;

public class PlayerManager {
	
	public static Stats getStat(Player p, int sId) {
		if(!p.getStats().containsKey(sId))
			p.getStats().put(sId, StatsManager.getBaseStat(p.getClasse(), sId));
		return p.getStats().get(sId);
	}
	
	public static String toALK(Player p) {
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
	
	public static String itemToALK(Player p) {
		String out = "";
		//TODO: When item will be more advance
		return out;
	}
	
	public static String toASK(Player p) {
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
	
	public static String itemToASK(Player p) {
		String out = "";
		//TODO: When item will be more advance
		return out;
	}
	
	public static String getXpToString(Player p) {
		String out = "";
		/*ExpStep loc1 = ExpManager.getExpStepByLevel(arg0.getLevel());
		ExpStep loc2 = ExpManager.getExpStepByLevel(arg0.getLevel() + 1);
		loc0 += arg0.getXp() + "," + loc1.getPlayer() + "," + loc2.getPlayer();*/
		return out;
	}
	
	public static List<String> getAsData(Player p) {
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
	
	public static int getPdvMax(Player p) {
		int lifeByPerso = 5;
		if(p.getClasse() == 1)
			lifeByPerso = 10;
		return 50 + (lifeByPerso * p.getLevel());
		//return 50 + (lifeByPerso * arg0.getLevel()) + arg0.getStats().get(StatsID.STATS_ADD_VITA).getValue();
	}
	
	public static String getDefaultGfx(Player p) {
		return ("" + p.getClasse() + (p.isSexe() ? "1" : "0")); 
	}
	
	public static String getGMData(Player p) {
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
	
	public static void generateBaseStats(Player p) {
		p.getStats().put(StatsID.STATS_ADD_AFLEE, new Stats(StatsID.STATS_ADD_AFLEE,0));
		p.getStats().put(StatsID.STATS_ADD_AGIL, new Stats(StatsID.STATS_ADD_AGIL,0));
		p.getStats().put(StatsID.STATS_ADD_CC, new Stats(StatsID.STATS_ADD_CC,0));
		p.getStats().put(StatsID.STATS_ADD_CHAN, new Stats(StatsID.STATS_ADD_CHAN,0));
		p.getStats().put(StatsID.STATS_ADD_DOMA, new Stats(StatsID.STATS_ADD_DOMA,0));
		p.getStats().put(StatsID.STATS_ADD_EC, new Stats(StatsID.STATS_ADD_EC,0));
		p.getStats().put(StatsID.STATS_ADD_FORC, new Stats(StatsID.STATS_ADD_FORC,0));
		p.getStats().put(StatsID.STATS_ADD_INIT, new Stats(StatsID.STATS_ADD_INIT,0));
		p.getStats().put(StatsID.STATS_ADD_INTE, new Stats(StatsID.STATS_ADD_INTE,0));
		p.getStats().put(StatsID.STATS_ADD_MAITRISE, new Stats(StatsID.STATS_ADD_MAITRISE,0));
		p.getStats().put(StatsID.STATS_ADD_MFLEE, new Stats(StatsID.STATS_ADD_MFLEE,0));
		p.getStats().put(StatsID.STATS_ADD_PA, new Stats(StatsID.STATS_ADD_PA,6));
		p.getStats().put(StatsID.STATS_ADD_PA2, new Stats(StatsID.STATS_ADD_PA2,0));
		p.getStats().put(StatsID.STATS_ADD_PDOM, new Stats(StatsID.STATS_ADD_PDOM,0));
		p.getStats().put(StatsID.STATS_ADD_PERDOM, new Stats(StatsID.STATS_ADD_PERDOM,0));
		p.getStats().put(StatsID.STATS_ADD_PM, new Stats(StatsID.STATS_ADD_PM,3));
		p.getStats().put(StatsID.STATS_ADD_PM2, new Stats(StatsID.STATS_ADD_PM2,0));
		p.getStats().put(StatsID.STATS_ADD_PO, new Stats(StatsID.STATS_ADD_PO,0));
		p.getStats().put(StatsID.STATS_ADD_PODS, new Stats(StatsID.STATS_ADD_PODS,1000));
		p.getStats().put(StatsID.STATS_ADD_PROS, new Stats(StatsID.STATS_ADD_PROS,0));
		p.getStats().put(StatsID.STATS_ADD_R_AIR, new Stats(StatsID.STATS_ADD_R_AIR,0));
		p.getStats().put(StatsID.STATS_ADD_R_EAU, new Stats(StatsID.STATS_ADD_R_EAU,0));
		p.getStats().put(StatsID.STATS_ADD_R_FEU, new Stats(StatsID.STATS_ADD_R_FEU,0));
		p.getStats().put(StatsID.STATS_ADD_R_NEU, new Stats(StatsID.STATS_ADD_R_NEU,0));
		p.getStats().put(StatsID.STATS_ADD_R_PVP_AIR, new Stats(StatsID.STATS_ADD_R_PVP_AIR,0));
		p.getStats().put(StatsID.STATS_ADD_R_PVP_EAU, new Stats(StatsID.STATS_ADD_R_PVP_EAU,0));
		p.getStats().put(StatsID.STATS_ADD_R_PVP_FEU, new Stats(StatsID.STATS_ADD_R_PVP_FEU,0));
		p.getStats().put(StatsID.STATS_ADD_R_PVP_NEU, new Stats(StatsID.STATS_ADD_R_PVP_NEU,0));
		p.getStats().put(StatsID.STATS_ADD_R_PVP_TER, new Stats(StatsID.STATS_ADD_R_PVP_TER,0));
		p.getStats().put(StatsID.STATS_ADD_R_TER, new Stats(StatsID.STATS_ADD_R_TER,0));
		p.getStats().put(StatsID.STATS_ADD_RP_AIR, new Stats(StatsID.STATS_ADD_RP_AIR,0));
		p.getStats().put(StatsID.STATS_ADD_RP_EAU, new Stats(StatsID.STATS_ADD_RP_EAU,0));
		p.getStats().put(StatsID.STATS_ADD_RP_FEU, new Stats(StatsID.STATS_ADD_RP_FEU,0));
		p.getStats().put(StatsID.STATS_ADD_RP_NEU, new Stats(StatsID.STATS_ADD_RP_NEU,0));
		p.getStats().put(StatsID.STATS_ADD_RP_PVP_AIR, new Stats(StatsID.STATS_ADD_RP_PVP_AIR,0));
		p.getStats().put(StatsID.STATS_ADD_RP_PVP_EAU, new Stats(StatsID.STATS_ADD_RP_PVP_EAU,0));
		p.getStats().put(StatsID.STATS_ADD_RP_PVP_FEU, new Stats(StatsID.STATS_ADD_RP_PVP_FEU,0));
		p.getStats().put(StatsID.STATS_ADD_RP_PVP_NEU, new Stats(StatsID.STATS_ADD_RP_PVP_NEU,0));
		p.getStats().put(StatsID.STATS_ADD_RP_PVP_TER, new Stats(StatsID.STATS_ADD_RP_PVP_TER,0));
		p.getStats().put(StatsID.STATS_ADD_RP_TER, new Stats(StatsID.STATS_ADD_RP_TER,0));
		p.getStats().put(StatsID.STATS_ADD_SAGE, new Stats(StatsID.STATS_ADD_SAGE,0));
		p.getStats().put(StatsID.STATS_ADD_SOIN, new Stats(StatsID.STATS_ADD_SOIN,0));
		p.getStats().put(StatsID.STATS_ADD_VIE, new Stats(StatsID.STATS_ADD_VIE,0));
		p.getStats().put(StatsID.STATS_ADD_VITA, new Stats(StatsID.STATS_ADD_VITA,0));
		p.getStats().put(StatsID.STATS_CREATURE, new Stats(StatsID.STATS_CREATURE,1));
		p.getStats().put(StatsID.STATS_MULTIPLY_DOMMAGE, new Stats(StatsID.STATS_MULTIPLY_DOMMAGE,0));
		p.getStats().put(StatsID.STATS_REM_AFLEE, new Stats(StatsID.STATS_REM_AFLEE,0));
		p.getStats().put(StatsID.STATS_REM_AGIL, new Stats(StatsID.STATS_REM_AGIL,0));
		p.getStats().put(StatsID.STATS_REM_CC, new Stats(StatsID.STATS_REM_CC,0));
		p.getStats().put(StatsID.STATS_REM_CHAN, new Stats(StatsID.STATS_REM_CHAN,0));
		p.getStats().put(StatsID.STATS_REM_DOMA, new Stats(StatsID.STATS_REM_DOMA,0));
		p.getStats().put(StatsID.STATS_REM_FORC, new Stats(StatsID.STATS_REM_FORC,0));
		p.getStats().put(StatsID.STATS_REM_INIT, new Stats(StatsID.STATS_REM_INIT,0));
		p.getStats().put(StatsID.STATS_REM_INTE, new Stats(StatsID.STATS_REM_INTE,0));
		p.getStats().put(StatsID.STATS_REM_MFLEE, new Stats(StatsID.STATS_REM_MFLEE,0));
		p.getStats().put(StatsID.STATS_REM_PA, new Stats(StatsID.STATS_REM_PA,0));
		p.getStats().put(StatsID.STATS_REM_PA2, new Stats(StatsID.STATS_REM_PA2,0));
		p.getStats().put(StatsID.STATS_REM_PM, new Stats(StatsID.STATS_REM_PM,0));
		p.getStats().put(StatsID.STATS_REM_PM2, new Stats(StatsID.STATS_REM_PM2,0));
		p.getStats().put(StatsID.STATS_REM_PO, new Stats(StatsID.STATS_REM_PO,0));
		p.getStats().put(StatsID.STATS_REM_PODS, new Stats(StatsID.STATS_REM_PODS,0));
		p.getStats().put(StatsID.STATS_REM_PROS, new Stats(StatsID.STATS_REM_PROS,0));
		p.getStats().put(StatsID.STATS_REM_R_AIR, new Stats(StatsID.STATS_REM_R_AIR,0));
		p.getStats().put(StatsID.STATS_REM_R_EAU, new Stats(StatsID.STATS_REM_R_EAU,0));
		p.getStats().put(StatsID.STATS_REM_R_FEU, new Stats(StatsID.STATS_REM_R_FEU,0));
		p.getStats().put(StatsID.STATS_REM_R_NEU, new Stats(StatsID.STATS_REM_R_NEU,0));
		p.getStats().put(StatsID.STATS_REM_R_TER, new Stats(StatsID.STATS_REM_R_TER,0));
		p.getStats().put(StatsID.STATS_REM_RP_AIR, new Stats(StatsID.STATS_REM_RP_AIR,0));
		p.getStats().put(StatsID.STATS_REM_RP_EAU, new Stats(StatsID.STATS_REM_RP_EAU,0));
		p.getStats().put(StatsID.STATS_REM_RP_FEU, new Stats(StatsID.STATS_REM_RP_FEU,0));
		p.getStats().put(StatsID.STATS_REM_RP_NEU, new Stats(StatsID.STATS_REM_RP_NEU,0));
		p.getStats().put(StatsID.STATS_REM_RP_PVP_AIR, new Stats(StatsID.STATS_REM_RP_PVP_AIR,0));
		p.getStats().put(StatsID.STATS_REM_RP_PVP_EAU, new Stats(StatsID.STATS_REM_RP_PVP_EAU,0));
		p.getStats().put(StatsID.STATS_REM_RP_PVP_FEU, new Stats(StatsID.STATS_REM_RP_PVP_FEU,0));
		p.getStats().put(StatsID.STATS_REM_RP_PVP_NEU, new Stats(StatsID.STATS_REM_RP_PVP_NEU,0));
		p.getStats().put(StatsID.STATS_REM_RP_PVP_TER, new Stats(StatsID.STATS_REM_RP_PVP_TER,0));
		p.getStats().put(StatsID.STATS_REM_RP_TER, new Stats(StatsID.STATS_REM_RP_TER,0));
		p.getStats().put(StatsID.STATS_REM_SAGE, new Stats(StatsID.STATS_REM_SAGE,0));
		p.getStats().put(StatsID.STATS_REM_SOIN, new Stats(StatsID.STATS_REM_SOIN,0));
		p.getStats().put(StatsID.STATS_REM_VITA, new Stats(StatsID.STATS_REM_VITA,0));
		p.getStats().put(StatsID.STATS_RETDOM, new Stats(StatsID.STATS_RETDOM,0));
		p.getStats().put(StatsID.STATS_TRAPDOM, new Stats(StatsID.STATS_TRAPDOM,0));
		p.getStats().put(StatsID.STATS_TRAPPER, new Stats(StatsID.STATS_TRAPPER,0));
	}
}
