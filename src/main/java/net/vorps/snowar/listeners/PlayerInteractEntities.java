package net.vorps.snowar.listeners;

import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.menu.Statistiques;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntities implements Listener {
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e){
		Player player = e.getPlayer();
		Entity entity = e.getRightClicked();
		PlayerData playerData = PlayerData.getPlayerData(player.getName());
        if(entity instanceof Villager){
            if(entity.getCustomName().equals(Settings.getNameVillagerStats())) new Statistiques(player);
            e.setCancelled(true);
        } else if(entity instanceof Player && playerData.getLife() == 0){
            Player target = (Player) e.getRightClicked();
            if(PlayerData.getPlayerData(target.getName()).getLife() != 0){
                playerData.setScoreboard(PlayerData.getPlayerData(target.getName()).getScoreboard());
                PlayerData.getPlayerData(target.getName()).getSpectator().add(player.getName());
                playerData.setPlayerView(target.getName());
            }
        }
	}
}
