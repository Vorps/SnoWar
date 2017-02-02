
package net.vorps.snowar.listeners;

import net.vorps.api.lang.Lang;
import net.vorps.api.type.GameState;
import net.vorps.api.utils.Limite;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.objects.MapParameter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class PlayerMove implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		Player player = e.getPlayer();
		if((GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)) && Limite.limite(player.getLocation(), MapParameter.getLimit())) {
            player.teleport(MapParameter.getSpawnLobby());
            player.sendMessage(Settings.getTitle() + Lang.getMessage("SNO_WAR.MOVE", PlayerData.getPlayerData(player.getName()).getLang()));
        }
	}
}
