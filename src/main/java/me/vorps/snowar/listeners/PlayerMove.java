
package me.vorps.snowar.listeners;

import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.objects.MapParameter;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.Limite;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		Player player = e.getPlayer();
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
		if(GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)) {
            if (Limite.limite(player.getLocation(), MapParameter.getLimit())) {
                player.teleport(MapParameter.getSpawnLobby());
                player.sendMessage(Settings.getTitle() + Lang.getMessage("SNO_WAR.MOVE", PlayerData.getPlayerData(player.getName()).getLang()));
            }
        }
	}
}
