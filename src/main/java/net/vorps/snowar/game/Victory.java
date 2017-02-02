package net.vorps.snowar.game;

import net.vorps.api.lang.Lang;
import net.vorps.api.lang.LangSetting;
import net.vorps.api.type.GameState;
import net.vorps.api.utils.Firework;
import net.vorps.api.utils.MessageTitle;
import net.vorps.api.utils.Title;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.objects.Stats;
import net.vorps.snowar.threads.Timers;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Victory {
	
	public static void onVictory(int state){
        Bukkit.getScheduler().cancelAllTasks();
		GameState.setState(GameState.FINISH);
        TreeMap<String, PlayerData> winnerTreeMap = PlayerData.triePlayerDataLife();
        ArrayList<String> winner = new ArrayList<>();
        int var = 0;
        for(String player : winnerTreeMap.keySet()){
            winner.add(player);
            if(++var == (PlayerData.getPlayerDataList().size() <= 3 ? 1 : 3)) break;
        }
        if(PlayerData.getPlayerData(winnerTreeMap.lastKey()).getLife() == Parameter.getLife() && state == 1){
            winner.clear();
            state = 3;
        }
		switch(state){
            case 1:
                HashMap<String, String> winnermsg = new HashMap<>();
                for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
                    String winnerLang = "";
                    for(String player : winner) winnerLang+= Lang.getMessage("SNO_WAR.VICTORY.WINNER", langSetting.getName(), new Lang.Args(Lang.Parameter.WINNER, player));
                    winnermsg.put(langSetting.getName(), winnerLang.substring(0, winnerLang.length()-1));
                }
                for(PlayerData playerData : PlayerData.getPlayerDataList().values()){
                    playerData.getPlayer().sendMessage(Lang.getMessage("SNO_WAR.VICTORY", playerData.getLang(), new Lang.Args(Lang.Parameter.WINNER, winnermsg.get(playerData.getLang()))));
                    new Title(MessageTitle.getMessageTitle("VICTORY.TITLE").getSubTitle(playerData.getLang(), new Lang.Args(Lang.Parameter.WINNER, "§c"+winner.get(0))), MessageTitle.getMessageTitle("VICTORY.TITLE").getSubTitle(playerData.getLang()), playerData.getPlayer());
                }
                break;
	    	case 2:
                PlayerData.broadCast("SNO_WAR.VICTORY.ABANDON", new Lang.Args(Lang.Parameter.PLAYER, winner.get(0)));
                for(PlayerData playerData : PlayerData.getPlayerDataList().values()) new Title(MessageTitle.getMessageTitle("VICTORY.ABANDON.TITLE").getTitle(playerData.getLang(), new Lang.Args(Lang.Parameter.PLAYER, winner.get(0))), MessageTitle.getMessageTitle("VICTORY.ABANDON.TITLE").getSubTitle(playerData.getLang()), playerData.getPlayer());
	    		break;
            case 3:
                PlayerData.broadCast("SNO_WAR.VICTORY.EQUAL");
                for(PlayerData playerData : PlayerData.getPlayerDataList().values()) new Title(MessageTitle.getMessageTitle("VICTORY.EQUAL.TITLE").getTitle(playerData.getLang()), MessageTitle.getMessageTitle("VICTORY.EQUAL.TITLE").getSubTitle(playerData.getLang()), playerData.getPlayer());
                break;
		default:
			break;
		}
        Firework.getFirework("firework").start(SnoWar.getInstance());
        for(PlayerData playerData : PlayerData.getPlayerDataList().values()){
            Stats.updateStats(playerData.getPlayer().getUniqueId(), playerData.getKill(), Parameter.getLife()-playerData.getLife(), playerData.getBonus(), playerData.getBallShoot(), playerData.getBallTouch(), (Parameter.getTimeGame()- Timers.getTime()), winner.contains(playerData.getPlayer().getName()) ? 1 : 0, winner.contains(playerData.getPlayer().getName()) ? 0 : 1);
            Parameter.getEarning().earning(playerData.getPlayer().getName(), playerData.getKill(), playerData.getBallTouch(), winner.contains(playerData.getPlayer().getName()));
        }
		Timers.run(Settings.getTimeFinish());
	}
}
