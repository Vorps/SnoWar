package me.vorps.snowar.listeners;

import me.vorps.snowar.game.GameState;
import me.vorps.snowar.menu.MenuSpectator;
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
            switch (item.getType()) {
                case SKULL_ITEM:
                    MenuSpectator.createMenu(player);
                    break;
                case BED:
                    // TODO: 25/07/2016 Quit
                    break;
                default:
                    break;
            }
        }
    }
}
