package me.vorps.snowar.menu;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuSpectator extends Menu{

	public MenuSpectator(String name){
        super(null, null, null);
		menu = Bukkit.createInventory(Bukkit.getPlayer(name), 18, Lang.getMessage("SNO_WAR.INVENTORY.SPECTATOR.TITLE", PlayerData.getPlayerData(name).getLang()));
		int index = 0;
		for(PlayerData playerData : PlayerData.getPlayerDataList().values()){
            if(playerData.getLife() != 0){
                menu.setItem(index, new Item(playerData.getPlayer().getName()).withName(playerData.toString()).get());
                index++;
            }
        }
        Bukkit.getPlayer(name).openInventory(menu);
	}

    @Override
    public void interractInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        switch(itemStack.getType()) {
            case SKULL_ITEM:
                if (itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.INVENTORY.SPECTATOR", PlayerData.getPlayerData(player.getName()).getLang()))) {
                    new MenuSpectator(player.getName());
                } else {
                    player.teleport(Bukkit.getPlayer(itemStack.getItemMeta().getDisplayName().substring(2)));
                }
                break;
            default:
                break;
        }
    }
}
