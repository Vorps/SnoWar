package me.vorps.snowar.game;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.objects.Stats;
import me.vorps.snowar.threads.Timers;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.lang.LangSetting;
import me.vorps.syluriapi.type.GameState;
import me.vorps.syluriapi.utils.Color;
import me.vorps.syluriapi.utils.Firework;
import me.vorps.syluriapi.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
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
                    for(String player : winner){
                        winnerLang+= Lang.getMessage("SNO_WAR.VICTORY.WINNER", langSetting.getName(), new Lang.Args(Lang.Parameter.WINNER, player));
                    }
                    winnermsg.put(langSetting.getName(), winnerLang.substring(0, winnerLang.length()-1));
                }
                for(PlayerData playerData : PlayerData.getPlayerDataList().values()){
                    playerData.getPlayer().sendMessage(Lang.getMessage("SNO_WAR.VICTORY", playerData.getLang(), new Lang.Args(Lang.Parameter.WINNER, winnermsg.get(playerData.getLang()))));
                    new Title(Lang.getMessage("SNO_WAR.VICTORY.TITLE", playerData.getLang(), new Lang.Args(Lang.Parameter.WINNER, "§c"+winner.get(0))), Lang.getMessage("SNO_WAR.VICTORY.SUBTITLE", playerData.getLang())).send(playerData.getPlayer());
                }
                break;
	    	case 2:
                PlayerData.broadCast("SNO_WAR.VICTORY.ABANDON", new Lang.Args(Lang.Parameter.PLAYER, winner.get(0)));
                for(PlayerData playerData : PlayerData.getPlayerDataList().values()){
                    new Title(Lang.getMessage("SNO_WAR.VICTORY.ABANDON.TITLE", playerData.getLang(), new Lang.Args(Lang.Parameter.PLAYER, winner.get(0))), Lang.getMessage("SNO_WAR.VICTORY.ABANDON.SUBTITLE", playerData.getLang())).send(playerData.getPlayer());
                }
	    		break;
            case 3:
                PlayerData.broadCast("SNO_WAR.VICTORY.EQUAL");
                for(PlayerData playerData : PlayerData.getPlayerDataList().values()){
                    new Title(Lang.getMessage("SNO_WAR.VICTORY.EQUAL.TITLE", playerData.getLang()), Lang.getMessage("SNO_WAR.VICTORY.EQUAL.SUBTITLE", playerData.getLang())).send(playerData.getPlayer());
                }
                break;
		default:
			break;
		}
        for(String player : winner){
            Player player_tmp = Bukkit.getPlayer(player);
            new Firework(player_tmp, player_tmp.getLocation().add(0, 5, 0), Color.values()[new Random().nextInt(17)].getColor(), Color.values()[new Random().nextInt(17)].getColor(), FireworkEffect.Type.values()[new Random().nextInt(5)], 20, 5);
        }
        for(PlayerData playerData : PlayerData.getPlayerDataList().values()){
            Stats.updateStats(playerData.getPlayer().getUniqueId(), playerData.getKill(), Parameter.getLife()-playerData.getLife(), playerData.getBonus(), playerData.getBallShoot(), playerData.getBallTouch(), (Parameter.getTimeGame()-Timers.getTime()), winner.contains(playerData.getPlayer().getName()) ? 1 : 0, winner.contains(playerData.getPlayer().getName()) ? 0 : 1);
            Parameter.getEarning().earning(playerData.getPlayer().getName(), playerData.getKill(), playerData.getBallTouch(), winner.contains(playerData.getPlayer().getName()));
        }
		Timers.run(Settings.getTimeFinish());
	}
}
