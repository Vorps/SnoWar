package me.vorps.snowar.listeners;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.menu.MenuSpectator;
import me.vorps.snowar.utils.Event;
import me.vorps.syluriapi.type.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryClick implements Listener {

	@EventHandler
	public void onPlayerInventoryClick(InventoryClickEvent e){
        if(!GameState.isState(GameState.INGAME) || e.getInventory().getSize() == 9){
            e.setCancelled(true);
        }
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if(item != null) {
            if(GameState.isState(GameState.INGAME)){
                switch (item.getType()) {
                    case SKULL_ITEM:
                        MenuSpectator.createMenu(player);
                        break;
                    default:
                        break;
                }
            } else {
                Event.interractItem(item, PlayerData.getPlayerData(player.getName()));
            }
        }
    }
}
