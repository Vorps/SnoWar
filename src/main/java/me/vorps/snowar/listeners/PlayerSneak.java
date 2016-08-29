package me.vorps.snowar.listeners;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.scoreboard.SbSpectator;
import me.vorps.syluriapi.type.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerSneak implements Listener {

	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent e){
		Player player = e.getPlayer();
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
		if(player.getGameMode() == GameMode.SPECTATOR && GameState.isState(GameState.INGAME) && playerData.getPlayerView() != null){
            playerData.setScoreboard(new SbSpectator(playerData.getLang()));
            PlayerData.getPlayerData(playerData.getPlayerView()).getSpectator().remove(player.getName());
            playerData.setPlayerView(null);
		}
	}
}
