package org.devemu.sql.manager;

import java.util.ArrayList;
import java.util.List;

import org.devemu.network.server.client.GameClient;
import org.devemu.program.Main;
import org.devemu.sql.entity.ExpStep;
import org.devemu.sql.entity.Player;
import org.devemu.sql.entity.Stats;
import org.devemu.utils.constants.StatsID;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

public class PlayerManager {
	public static String toALK(Player arg0) {
		String loc1 = "";
		//guid;name;level;gfx;colors0;colors1;colors2;stuffALK;isSeller;gameId;isDead;DeathCount;levelMax;
		loc1 += (arg0.getGuid() + ";"
				+ arg0.getName() + ";"
				+ arg0.getLevel() + ";"
				+ (arg0.getGfx() == -1 ? getDefaultGfx(arg0) : arg0.getGfx()) + ";");
		for(int loc2 : arg0.getColors()) {
			loc1 += ((loc2 != -1 ? Integer.toHexString(loc2) : "-1") + ";");
		}
		loc1 += ((itemToALK(arg0)) + ";"
				+ (arg0.isMarchant() ? "1" : "0") + ";"
				+ ("" + Main.getGuid()) + ";"
				+ (arg0.isDead() ? "1" : "0") + ";"
				+ ("" + arg0.getCountDead()) + ";"
				+ ExpManager.getCount() + ";");
		return loc1;
	}
	
	public static String itemToALK(Player arg0) {
		String loc1 = "";
		//TODO: When item will be more advance
		return loc1;
	}
	
	public static List<String> toASK(Player arg0) {
		List<String> loc1 = new ArrayList<String>();
		//guid|name|level|classe|sexe|gfxid|color1|color2|color3|itemToASK
		loc1.add("" + arg0.getGuid());
		loc1.add(arg0.getName());
		loc1.add("" + arg0.getLevel());
		loc1.add(arg0.isSexe() ? "1" : "0");
		loc1.add(arg0.getGfx() == -1 ? getDefaultGfx(arg0) : ("" + arg0.getGfx()));
		for(int loc2 : arg0.getColors()) {
			loc1.add(loc2 != -1 ? Integer.toHexString(loc2) : "-1");
		}
		loc1.add(itemToASK(arg0));
		return loc1;
	}
	
	public static String itemToASK(Player arg0) {
		String loc1 = "";
		//TODO: When item will be more advance
		return loc1;
	}
	
	public static String getXpToString(Player arg0) {
		String loc0 = "";
		ExpStep loc1 = ExpManager.getExpStepByLevel(arg0.getLevel());
		ExpStep loc2 = ExpManager.getExpStepByLevel(arg0.getLevel() + 1);
		loc0 += arg0.getXp() + "," + loc1.getPlayer() + "," + loc2.getPlayer();
		return loc0;
	}
	
	public static List<String> getAsData(Player arg0) {
		List<String> loc0 = new ArrayList<String>();
		loc0.add("" + arg0.getKamas());
		loc0.add("" + arg0.getCapitals());
		loc0.add("" + arg0.getSpellPoints());
		loc0.add(arg0.getAlignement().getOrdre() + "~" + arg0.getAlignement().getOrdre() + "," +
				arg0.getAlignement().getLevel() + "," + arg0.getAlignement().getGrade() +
				arg0.getAlignement().getHonor() + "," + arg0.getAlignement().getDeshonor() + (arg0.isShowingWings() ? "1" : "0"));
		loc0.add(arg0.getPdv() + "," + getPdvMax(arg0));
		return loc0;
	}
	
	public static int getPdvMax(Player arg0) {
		int lifeByPerso = 5;
		if(arg0.getClasse() == 1)
			lifeByPerso = 10;
		return 50 + (lifeByPerso * arg0.getLevel());
		//return 50 + (lifeByPerso * arg0.getLevel()) + arg0.getStats().get(StatsID.STATS_ADD_VITA).getValue();
	}
	
	public static String getDefaultGfx(Player arg0) {
		return ("" + arg0.getClasse() + (arg0.isSexe() ? "1" : "0")); 
	}
	
	public static Player getById(int arg0) {
		Session loc1 = Main.getSqlSession();
		Player loc2 = (Player) loc1.get(Player.class, arg0);
		loc1.close();
		return loc2;
	}
	
	public static int getNextId() {
		Session loc1 = Main.getSqlSession();
		Object loc3 = loc1.createCriteria(Player.class).setProjection(Projections.max("guid")).uniqueResult();
		int loc2 = 0;
		if(loc3 != null)
			loc2 = (int) loc3;
		loc1.close();
		return (loc2 + 1);
	}
	
	public static void generateBaseStats(Player arg0) {
		int loc1 = StatsManager.getNextId();
		arg0.getStats().put(StatsID.STATS_ADD_AFLEE, new Stats(loc1++,StatsID.STATS_ADD_AFLEE,0));
		arg0.getStats().put(StatsID.STATS_ADD_AGIL, new Stats(loc1++,StatsID.STATS_ADD_AGIL,0));
		arg0.getStats().put(StatsID.STATS_ADD_CC, new Stats(loc1++,StatsID.STATS_ADD_CC,0));
		arg0.getStats().put(StatsID.STATS_ADD_CHAN, new Stats(loc1++,StatsID.STATS_ADD_CHAN,0));
		arg0.getStats().put(StatsID.STATS_ADD_DOMA, new Stats(loc1++,StatsID.STATS_ADD_DOMA,0));
		arg0.getStats().put(StatsID.STATS_ADD_EC, new Stats(loc1++,StatsID.STATS_ADD_EC,0));
		arg0.getStats().put(StatsID.STATS_ADD_FORC, new Stats(loc1++,StatsID.STATS_ADD_FORC,0));
		arg0.getStats().put(StatsID.STATS_ADD_INIT, new Stats(loc1++,StatsID.STATS_ADD_INIT,0));
		arg0.getStats().put(StatsID.STATS_ADD_INTE, new Stats(loc1++,StatsID.STATS_ADD_INTE,0));
		arg0.getStats().put(StatsID.STATS_ADD_MAITRISE, new Stats(loc1++,StatsID.STATS_ADD_MAITRISE,0));
		arg0.getStats().put(StatsID.STATS_ADD_MFLEE, new Stats(loc1++,StatsID.STATS_ADD_MFLEE,0));
		arg0.getStats().put(StatsID.STATS_ADD_PA, new Stats(loc1++,StatsID.STATS_ADD_PA,6));
		arg0.getStats().put(StatsID.STATS_ADD_PA2, new Stats(loc1++,StatsID.STATS_ADD_PA2,0));
		arg0.getStats().put(StatsID.STATS_ADD_PDOM, new Stats(loc1++,StatsID.STATS_ADD_PDOM,0));
		arg0.getStats().put(StatsID.STATS_ADD_PERDOM, new Stats(loc1++,StatsID.STATS_ADD_PERDOM,0));
		arg0.getStats().put(StatsID.STATS_ADD_PM, new Stats(loc1++,StatsID.STATS_ADD_PM,3));
		arg0.getStats().put(StatsID.STATS_ADD_PM2, new Stats(loc1++,StatsID.STATS_ADD_PM2,0));
		arg0.getStats().put(StatsID.STATS_ADD_PO, new Stats(loc1++,StatsID.STATS_ADD_PO,0));
		arg0.getStats().put(StatsID.STATS_ADD_PODS, new Stats(loc1++,StatsID.STATS_ADD_PODS,1000));
		arg0.getStats().put(StatsID.STATS_ADD_PROS, new Stats(loc1++,StatsID.STATS_ADD_PROS,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_AIR, new Stats(loc1++,StatsID.STATS_ADD_R_AIR,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_EAU, new Stats(loc1++,StatsID.STATS_ADD_R_EAU,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_FEU, new Stats(loc1++,StatsID.STATS_ADD_R_FEU,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_NEU, new Stats(loc1++,StatsID.STATS_ADD_R_NEU,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_PVP_AIR, new Stats(loc1++,StatsID.STATS_ADD_R_PVP_AIR,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_PVP_EAU, new Stats(loc1++,StatsID.STATS_ADD_R_PVP_EAU,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_PVP_FEU, new Stats(loc1++,StatsID.STATS_ADD_R_PVP_FEU,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_PVP_NEU, new Stats(loc1++,StatsID.STATS_ADD_R_PVP_NEU,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_PVP_TER, new Stats(loc1++,StatsID.STATS_ADD_R_PVP_TER,0));
		arg0.getStats().put(StatsID.STATS_ADD_R_TER, new Stats(loc1++,StatsID.STATS_ADD_R_TER,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_AIR, new Stats(loc1++,StatsID.STATS_ADD_RP_AIR,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_EAU, new Stats(loc1++,StatsID.STATS_ADD_RP_EAU,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_FEU, new Stats(loc1++,StatsID.STATS_ADD_RP_FEU,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_NEU, new Stats(loc1++,StatsID.STATS_ADD_RP_NEU,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_PVP_AIR, new Stats(loc1++,StatsID.STATS_ADD_RP_PVP_AIR,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_PVP_EAU, new Stats(loc1++,StatsID.STATS_ADD_RP_PVP_EAU,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_PVP_FEU, new Stats(loc1++,StatsID.STATS_ADD_RP_PVP_FEU,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_PVP_NEU, new Stats(loc1++,StatsID.STATS_ADD_RP_PVP_NEU,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_PVP_TER, new Stats(loc1++,StatsID.STATS_ADD_RP_PVP_TER,0));
		arg0.getStats().put(StatsID.STATS_ADD_RP_TER, new Stats(loc1++,StatsID.STATS_ADD_RP_TER,0));
		arg0.getStats().put(StatsID.STATS_ADD_SAGE, new Stats(loc1++,StatsID.STATS_ADD_SAGE,0));
		arg0.getStats().put(StatsID.STATS_ADD_SOIN, new Stats(loc1++,StatsID.STATS_ADD_SOIN,0));
		arg0.getStats().put(StatsID.STATS_ADD_VIE, new Stats(loc1++,StatsID.STATS_ADD_VIE,0));
		arg0.getStats().put(StatsID.STATS_ADD_VITA, new Stats(loc1++,StatsID.STATS_ADD_VITA,0));
		arg0.getStats().put(StatsID.STATS_CREATURE, new Stats(loc1++,StatsID.STATS_CREATURE,1));
		arg0.getStats().put(StatsID.STATS_MULTIPLY_DOMMAGE, new Stats(loc1++,StatsID.STATS_MULTIPLY_DOMMAGE,0));
		arg0.getStats().put(StatsID.STATS_REM_AFLEE, new Stats(loc1++,StatsID.STATS_REM_AFLEE,0));
		arg0.getStats().put(StatsID.STATS_REM_AGIL, new Stats(loc1++,StatsID.STATS_REM_AGIL,0));
		arg0.getStats().put(StatsID.STATS_REM_CC, new Stats(loc1++,StatsID.STATS_REM_CC,0));
		arg0.getStats().put(StatsID.STATS_REM_CHAN, new Stats(loc1++,StatsID.STATS_REM_CHAN,0));
		arg0.getStats().put(StatsID.STATS_REM_DOMA, new Stats(loc1++,StatsID.STATS_REM_DOMA,0));
		arg0.getStats().put(StatsID.STATS_REM_FORC, new Stats(loc1++,StatsID.STATS_REM_FORC,0));
		arg0.getStats().put(StatsID.STATS_REM_INIT, new Stats(loc1++,StatsID.STATS_REM_INIT,0));
		arg0.getStats().put(StatsID.STATS_REM_INTE, new Stats(loc1++,StatsID.STATS_REM_INTE,0));
		arg0.getStats().put(StatsID.STATS_REM_MFLEE, new Stats(loc1++,StatsID.STATS_REM_MFLEE,0));
		arg0.getStats().put(StatsID.STATS_REM_PA, new Stats(loc1++,StatsID.STATS_REM_PA,0));
		arg0.getStats().put(StatsID.STATS_REM_PA2, new Stats(loc1++,StatsID.STATS_REM_PA2,0));
		arg0.getStats().put(StatsID.STATS_REM_PM, new Stats(loc1++,StatsID.STATS_REM_PM,0));
		arg0.getStats().put(StatsID.STATS_REM_PM2, new Stats(loc1++,StatsID.STATS_REM_PM2,0));
		arg0.getStats().put(StatsID.STATS_REM_PO, new Stats(loc1++,StatsID.STATS_REM_PO,0));
		arg0.getStats().put(StatsID.STATS_REM_PODS, new Stats(loc1++,StatsID.STATS_REM_PODS,0));
		arg0.getStats().put(StatsID.STATS_REM_PROS, new Stats(loc1++,StatsID.STATS_REM_PROS,0));
		arg0.getStats().put(StatsID.STATS_REM_R_AIR, new Stats(loc1++,StatsID.STATS_REM_R_AIR,0));
		arg0.getStats().put(StatsID.STATS_REM_R_EAU, new Stats(loc1++,StatsID.STATS_REM_R_EAU,0));
		arg0.getStats().put(StatsID.STATS_REM_R_FEU, new Stats(loc1++,StatsID.STATS_REM_R_FEU,0));
		arg0.getStats().put(StatsID.STATS_REM_R_NEU, new Stats(loc1++,StatsID.STATS_REM_R_NEU,0));
		arg0.getStats().put(StatsID.STATS_REM_R_TER, new Stats(loc1++,StatsID.STATS_REM_R_TER,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_AIR, new Stats(loc1++,StatsID.STATS_REM_RP_AIR,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_EAU, new Stats(loc1++,StatsID.STATS_REM_RP_EAU,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_FEU, new Stats(loc1++,StatsID.STATS_REM_RP_FEU,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_NEU, new Stats(loc1++,StatsID.STATS_REM_RP_NEU,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_PVP_AIR, new Stats(loc1++,StatsID.STATS_REM_RP_PVP_AIR,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_PVP_EAU, new Stats(loc1++,StatsID.STATS_REM_RP_PVP_EAU,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_PVP_FEU, new Stats(loc1++,StatsID.STATS_REM_RP_PVP_FEU,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_PVP_NEU, new Stats(loc1++,StatsID.STATS_REM_RP_PVP_NEU,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_PVP_TER, new Stats(loc1++,StatsID.STATS_REM_RP_PVP_TER,0));
		arg0.getStats().put(StatsID.STATS_REM_RP_TER, new Stats(loc1++,StatsID.STATS_REM_RP_TER,0));
		arg0.getStats().put(StatsID.STATS_REM_SAGE, new Stats(loc1++,StatsID.STATS_REM_SAGE,0));
		arg0.getStats().put(StatsID.STATS_REM_SOIN, new Stats(loc1++,StatsID.STATS_REM_SOIN,0));
		arg0.getStats().put(StatsID.STATS_REM_VITA, new Stats(loc1++,StatsID.STATS_REM_VITA,0));
		arg0.getStats().put(StatsID.STATS_RETDOM, new Stats(loc1++,StatsID.STATS_RETDOM,0));
		arg0.getStats().put(StatsID.STATS_TRAPDOM, new Stats(loc1++,StatsID.STATS_TRAPDOM,0));
		arg0.getStats().put(StatsID.STATS_TRAPPER, new Stats(loc1++,StatsID.STATS_TRAPPER,0));
	}
	
	public static void create(Player arg1,GameClient arg2) {
		Session loc1 = Main.getSqlSession();
		arg2.getAcc().getPlayers().add(arg1);
		loc1.beginTransaction();
		loc1.saveOrUpdate(arg2.getAcc());
		loc1.getTransaction().commit();
		loc1.close();
	}
	
	public static void delete(Player arg1,GameClient arg2) {
		Session loc1 = Main.getSqlSession();
		AccountManager.removePlayer(arg2.getAcc(), arg1.getGuid());
		loc1.beginTransaction();
		loc1.delete(arg1);
		loc1.getTransaction().commit();
		loc1.beginTransaction();
		loc1.saveOrUpdate(arg2.getAcc());
		loc1.getTransaction().commit();
		loc1.close();
	}
}
