package me.vorps.snowar.listeners;

import me.vorps.snowar.Data;
import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.scoreboard.SbLobby;
import me.vorps.snowar.threads.Timers;
import me.vorps.snowar.utils.Lang;
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
        new PlayerData(player.getUniqueId());
        PlayerData.broadCast("SNO_WAR.JOIN", new Lang.Args(Lang.Parameter.PLAYER, player.getName()), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.size()), new Lang.Args(Lang.Parameter.NBR_MAX_PLAYER, ""+ Data.getMaxPlayer()));
        if((GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)) && PlayerData.size() >= Data.getMinPlayer()){
            int time = getTime();
            System.out.println(time);
            if(time != -1 && Timers.getTime() >= time){
                if(GameState.isState(GameState.WAITING)){
                    GameState.setState(GameState.INSTART);
                    PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                        playerData.getScoreboard().remove("waiting");
                        playerData.getScoreboard().add("time", Lang.getMessage(SbLobby.getKey(time), playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, ""+time)), 5);
                    });
                    Timers.run(time);
                } else {
                    Timers.setTime(time);
                }
            }
		}
        PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> playerData.getScoreboard().updateValue("player", Lang.getMessage("SNO_WAR.SB.PLAYER", playerData.getLang(), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.size()))));
        e.setJoinMessage(null);
	}

    private static int getTime(){
        int time = -1;
        if(((float)(PlayerData.size()-1)/Data.getMaxPlayer())*100 < 25.0 && ((float)PlayerData.size()/Data.getMaxPlayer())*100 >= 25.0){
            time = Settings.getTimeStart();
        }
        if(((float)(PlayerData.size()-1)/Data.getMaxPlayer())*100 < 50.0 && ((float)PlayerData.size()/Data.getMaxPlayer())*100 >= 50.0){
            time = Settings.getTimeStart()/2;
        }
        if(((float)(PlayerData.size()-1)/Data.getMaxPlayer())*100 < 75.0 && ((float)PlayerData.size()/Data.getMaxPlayer())*100 >= 75.0){
            time = Settings.getTimeStart()/4;
        }
        if(PlayerData.size() == Data.getMaxPlayer()){
            time = Settings.getTimeStart()/6;
        }
        return time;
    }
}
