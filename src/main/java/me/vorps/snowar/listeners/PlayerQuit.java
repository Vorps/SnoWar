package me.vorps.snowar.listeners;

import me.vorps.snowar.Data;
import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.scoreboard.SbLobby;
import me.vorps.snowar.threads.ThreadInStart;
import me.vorps.snowar.threads.Timers;
import me.vorps.snowar.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
		PlayerData playerData = PlayerData.getPlayerData(player.getName());
		if(GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)){
            PlayerData.broadCast("SNO_WAR.QUIT.LOBBY", new Lang.Args(Lang.Parameter.PLAYER, ""+player.getName()), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+(PlayerData.size()-1)), new Lang.Args(Lang.Parameter.NBR_MAX_PLAYER, ""+ Data.getMaxPlayer()));
            PlayerData.getPlayerDataList().values().forEach((PlayerData playerDataList) -> playerDataList.getScoreboard().updateValue("player", Lang.getMessage("SNO_WAR.SB.PLAYER", playerDataList.getLang(), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.size()))));
            if(GameState.isState(GameState.INSTART) && ((float)(PlayerData.size()-1))/Data.getMaxPlayer()*100 < 25){
                GameState.setState(GameState.WAITING);
                PlayerData.getPlayerDataList().values().forEach((PlayerData playerDataList) -> {
                    playerDataList.getScoreboard().add("waiting", Lang.getMessage("SNO_WAR.SB.WAITING", playerData.getLang()), 3);
                    playerDataList.getScoreboard().remove("time");
                });
				Bukkit.getScheduler().cancelTask(ThreadInStart.getTask());
                Timers.setTime(Settings.getTimeStart());
                PlayerData.getPlayerDataList().values().forEach((PlayerData playerDataList) -> playerDataList.getScoreboard().updateValue("time",  Lang.getMessage(SbLobby.getKey(Timers.getTime()), playerDataList.getLang(), new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()))));
			}
		} else {
            if(playerData.getLife() != 0){
                PlayerData.removePlayerInGame();
            }
            if(PlayerData.getPlayerInGame() == 1){
                // TODO: 22/07/2016 Finish
            }
            if(PlayerData.size()-1 == 0){
                Bukkit.shutdown();
            }
        }
        e.setQuitMessage(null);
        playerData.removePlayerData();
    }
}
