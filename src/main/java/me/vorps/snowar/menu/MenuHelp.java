package me.vorps.snowar.menu;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.bonus.Bonus;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.menu.Item;
import me.vorps.syluriapi.menu.MenuRecursive;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Project SnoWar Created by Vorps on 08/08/2016 at 19:55.
 */
public class MenuHelp extends MenuRecursive {

    private MenuHelp(final PlayerData playerData, final ArrayList<Item> list){
        super(null,  Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU_HELP_TITLE", playerData.getLang())), null, list, playerData.getLang(), 9, 0);
        this.initMenu(playerData.getPlayer(), 1);
        playerData.getPlayer().openInventory(super.menu);
    }


    @Override
    public void initMenu(final Player player, final int page){
        super.menu.clear();
        super.getPage(page);
        player.updateInventory();
    }

    public int getSize(){
        return menu.getSize();
    }

    public static void createMenu(PlayerData playerData){
        ArrayList<Item> list = new ArrayList<>();
        Bonus.getBonusList().forEach((Bonus bonus) -> {
            list.add(me.vorps.syluriapi.utils.Item.getItem(bonus.getIcon(), playerData.getLang()));
        });
        new MenuHelp(playerData, list);
    }

    @Override
    public void interractInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        switch (itemStack.getType()) {
            case ARROW:
                player.closeInventory();
                break;
            case PAPER:
                initMenu(player, page+1);
                break;
            case EMPTY_MAP:
                initMenu(player, page-1);
                break;
            default:
                break;
        }
    }

}
