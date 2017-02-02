package net.vorps.snowar.menu;

import net.vorps.api.lang.Lang;
import net.vorps.api.menu.Item;
import net.vorps.api.menu.MenuRecursive;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.SnoWar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MenuSpectator extends MenuRecursive{

	public MenuSpectator(final Player player, final ArrayList<Item> list){
        super(null, Bukkit.createInventory(player, list.size() > 9 ? list.size() > 18 ? 36 : 27 : 18, Lang.getMessage("SNO_WAR.INVENTORY.SPECTATOR.TITLE", PlayerData.getPlayerData(player.getName()).getLang())), null, list,PlayerData.getPlayerData(player.getName()).getLang(), 9, 0, SnoWar.getInstance());
        this.initMenu(player, 1);
        player.openInventory(super.menu);
	}

    @Override
    public void initMenu(final Player player, final int page){
        super.menu.clear();
        getPage(page);
        player.updateInventory();
    }

    public static void createMenu(final Player player){
        ArrayList<Item> list = new ArrayList<>();
        PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {if(playerData.getLife() != 0) list.add(new Item(playerData.getPlayer().getName()).withName(playerData.toString()));});
        new MenuSpectator(player, list);
    }


    @Override
    public void interractInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        switch(itemStack.getType()) {
            case ARROW:
                player.closeInventory();
                break;
            case PAPER:
                initMenu(player, page+1);
                break;
            case EMPTY_MAP:
                initMenu(player, page-1);
                break;
            case SKULL_ITEM:
                if (itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.INVENTORY.SPECTATOR", PlayerData.getPlayerData(player.getName()).getLang()))) {
                    MenuSpectator.createMenu(player);
                } else {
                    player.teleport(Bukkit.getPlayer(itemStack.getItemMeta().getDisplayName().substring(2)));
                }
                break;
            default:
                break;
        }
    }
}
