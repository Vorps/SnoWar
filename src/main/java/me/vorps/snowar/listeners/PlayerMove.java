
package me.vorps.snowar.listeners;

import me.vorps.rushvolcano.objects.MapParameter;
import me.vorps.fortycube.utils.Lang;
import me.vorps.rushvolcano.PlayerData;
import me.vorps.rushvolcano.objects.Team;
import me.vorps.rushvolcano.Settings;
import me.vorps.fortycube.type.GameState;
import me.vorps.fortycube.utils.Limite;
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
            boolean state = false;
            if (playerData.getTeam() == null){
                if (Limite.limite(player.getLocation(), MapParameter.getLimit())) {
                    player.teleport(MapParameter.getSpawnLobby());
                    state = true;
                }
            } else {
                if (playerData.getTeam().getSpawnLobbyTeam() != null) {
                    Team team = playerData.getTeam();
                    if (Limite.limite(player.getLocation(), team.getLimit())) {
                        player.teleport(playerData.getTeam().getSpawnLobbyTeam());
                        state = true;
                    }
                } else {
                    if (Limite.limite(player.getLocation(), MapParameter.getLimit())) {
                        player.teleport(MapParameter.getSpawnLobby());
                        state = true;
                    }
                }
            }
            if(state){
                player.sendMessage(Settings.getTitle() + Lang.getMessage("RUSH_VOLCANO.MOVE", PlayerData.getPlayerData(player.getName()).getLang()));
            }
        }
	}
}
