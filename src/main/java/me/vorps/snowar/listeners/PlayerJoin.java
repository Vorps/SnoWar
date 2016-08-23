package me.vorps.snowar.listeners;

import me.vorps.snowar.Data;
import me.vorps.snowar.game.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.threads.Timers;
import me.vorps.syluriapi.lang.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class PlayerJoin implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
        PlayerData playerDataInstance = new PlayerData(player.getUniqueId());
        if(playerDataInstance.getLife() != 0){
            PlayerData.broadCast("SNO_WAR.JOIN", new Lang.Args(Lang.Parameter.PLAYER, player.getName()), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.getPlayerInGame()), new Lang.Args(Lang.Parameter.NBR_MAX_PLAYER, ""+ Data.getNbPlayerMax()));
        }
        if((GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)) && PlayerData.getPlayerInGame() >= Data.getNbPlayerMin() && PlayerData.getPlayerInGame() > 1){
            int time = getTime();
            if(time != -1 && Timers.getTime() >= time){
                if(GameState.isState(GameState.WAITING)){
                    GameState.setState(GameState.INSTART);
                    Timers.run(time);
                } else {
                    Timers.setTime(time);
                }
            }
		}
        PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> playerData.getScoreboard().updateValue("player", Lang.getMessage("SNO_WAR.SB.PLAYER", playerData.getLang(), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.getPlayerInGame()))));
        e.setJoinMessage(null);
	}

    private static int getTime(){
        int time = -1;
        if(((float)(PlayerData.getPlayerInGame()-1)/Data.getNbPlayerMax())*100 < 25.0 && ((float)PlayerData.getPlayerInGame()/Data.getNbPlayerMax())*100 >= 25.0){
            time = Settings.getTimeStart();
        }
        if(((float)(PlayerData.getPlayerInGame()-1)/Data.getNbPlayerMax())*100 < 50.0 && ((float)PlayerData.getPlayerInGame()/Data.getNbPlayerMax())*100 >= 50.0){
            time = Settings.getTimeStart()/2;
        }
        if(((float)(PlayerData.getPlayerInGame()-1)/Data.getNbPlayerMax())*100 < 75.0 && ((float)PlayerData.getPlayerInGame()/Data.getNbPlayerMax())*100 >= 75.0){
            time = Settings.getTimeStart()/4;
        }
        if(PlayerData.getPlayerInGame() == Data.getNbPlayerMax()){
            time = Settings.getTimeStart()/6;
        }
        return time;
    }
}
