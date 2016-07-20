package me.vorps.snowar.listeners;

import me.vorps.snowar.Data;
import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		new PlayerData(player.getUniqueId());
		if(GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)){
			PlayerData.broadCast("SNO_WAR.JOIN", new Lang.Args(Lang.Parameter.PLAYER, player.getName()), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.size()), new Lang.Args(Lang.Parameter.NBR_MAX_PLAYER, ""+ Data.getNumberPlayer()));
			if(Bukkit.getOnlinePlayers().size() == Data.getNumberPlayer()){
				GameState.setState(GameState.INSTART);
				//Timers.run(Settings.getTimeStart());
			}
			//LobbyManager.updateInventory();
		}
        e.setJoinMessage("");
	}
}
