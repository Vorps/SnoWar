package me.vorps.snowar.listeners;

import me.vorps.snowar.Data;
import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.threads.Timers;
import me.vorps.snowar.utils.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
        new PlayerData(player.getUniqueId());
        PlayerData.broadCast("SNO_WAR.JOIN", new Lang.Args(Lang.Parameter.PLAYER, player.getName()), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.size()), new Lang.Args(Lang.Parameter.NBR_MAX_PLAYER, ""+ Data.getNumberPlayer()));
        if((GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)) && PlayerData.size() >= Data.getMinPlayer()){
            int time = getTime();
            if(time != -1 && Timers.getTime() > time){
                if(GameState.isState(GameState.WAITING)){
                    GameState.setState(GameState.INSTART);
                    Timers.run(time);
                } else {
                    Timers.setTime(time);
                }
            }
		}
        e.setJoinMessage(null);
	}

    private static int getTime(){
        int time = -1;
        if((PlayerData.size()-1)/Data.getMaxPlayer()*100 < 25 && PlayerData.size()/Data.getMaxPlayer()*100 >= 25){
            time = 120;
        }
        if((PlayerData.size()-1)/Data.getMaxPlayer()*100 < 50 && PlayerData.size()/Data.getMaxPlayer()*100 >= 50){
            time = 60;
        }
        if((PlayerData.size()-1)/Data.getMaxPlayer()*100 < 75 && PlayerData.size()/Data.getMaxPlayer()*100 >= 75){
            time = 30;
        }
        if(PlayerData.size() == Data.getMaxPlayer()){
            time = 15;
        }
        return time;
    }
}
