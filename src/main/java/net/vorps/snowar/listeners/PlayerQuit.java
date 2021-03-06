package net.vorps.snowar.listeners;

import net.vorps.api.lang.Lang;
import net.vorps.api.type.GameState;
import net.vorps.snowar.Data;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.threads.ThreadInStart;
import net.vorps.snowar.threads.Timers;
import net.vorps.snowar.game.Victory;
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
            PlayerData.broadCast("SNO_WAR.QUIT.LOBBY", new Lang.Args(Lang.Parameter.PLAYER, ""+player.getName()), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+(PlayerData.getPlayerInGame()-1)), new Lang.Args(Lang.Parameter.NBR_MAX_PLAYER, ""+ Data.getNbPlayerMax()));
            if(GameState.isState(GameState.INSTART) && ((float)(playerData.getLife() > 0 ? PlayerData.getPlayerInGame()-1 : PlayerData.getPlayerInGame()))/Data.getNbPlayerMax()*100 < 25){
                GameState.setState(GameState.WAITING);
                Bukkit.getScheduler().cancelTask(ThreadInStart.getTask());
                PlayerData.getPlayerDataList().values().forEach((PlayerData playerDataList) -> {
                    playerDataList.getScoreboard().remove("time");
                    playerDataList.getScoreboard().add("waiting", Lang.getMessage("SNO_WAR.SB.WAITING", playerData.getLang()), 3);
                });
                Timers.setTime(Settings.getTimeStart());
			}
            if(playerData.getLife() > 0) PlayerData.removePlayerInGame();
		} else {
            if(playerData.getLife() > 0){
                playerData.resetLife();
                PlayerData.removePlayerInGame();
            }
            if(PlayerData.getPlayerInGame() == 2 && playerData.getLife() > 0){
                Victory.onVictory(2);
            }

            if(GameState.isState(GameState.FINISH) && PlayerData.getPlayerDataList().size() == 1 || GameState.isState(GameState.INGAME) && PlayerData.getPlayerInGame() == 1){
                Bukkit.shutdown();
            }
        }
        e.setQuitMessage(null);
        playerData.removePlayerData();
    }
}
