package net.vorps.snowar.listeners;

import net.vorps.api.lang.Lang;
import net.vorps.api.type.GameState;
import net.vorps.snowar.Data;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.threads.Timers;
import net.vorps.snowar.utils.TimeStart;
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
        if(playerDataInstance.getLife() != 0) PlayerData.broadCast("SNO_WAR.JOIN", new Lang.Args(Lang.Parameter.PLAYER, player.getName()), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.getPlayerInGame()), new Lang.Args(Lang.Parameter.NBR_MAX_PLAYER, ""+ Data.getNbPlayerMax()));
        if((GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)) && PlayerData.getPlayerInGame() >= Data.getNbPlayerMin() && PlayerData.getPlayerInGame() > 2){
            int time = TimeStart.getTime();
            if(time != -1 && Timers.getTime() >= time){
                if(GameState.isState(GameState.WAITING)) {
                    GameState.setState(GameState.INSTART);
                    Timers.run(time);
                } else Timers.setTime(time);
            }
		}
        PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> playerData.getScoreboard().updateValue("player", Lang.getMessage("SNO_WAR.SB.PLAYER", playerData.getLang(), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.getPlayerInGame()))));
        e.setJoinMessage(null);
	}
}
