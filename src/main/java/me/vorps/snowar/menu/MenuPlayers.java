package me.vorps.snowar.menu;

import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scenario.Scenario;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.menu.Item;
import me.vorps.syluriapi.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 06/08/2016 at 20:51.
 */
public class MenuPlayers extends Menu {

    public MenuPlayers(PlayerData playerData){
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU_PLAYER_TITLE", playerData.getLang())), null);
        super.menu.setItem(3, new Item(397).withName(Lang.getMessage("SNO_WAR.SCENARIO.PLAYER_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.PLAYER.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+ Data.getNbPlayerMax()))}).get());
        super.menu.setItem(5, new Item(Material.GOLDEN_CARROT).withName(Lang.getMessage("SNO_WAR.SCENARIO.LIFE_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.PLAYER.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+ Parameter.getLife()))}).get());
        super.menu.setItem(9, new Item(Material.ARROW).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", playerData.getLang())).get());
        playerData.getPlayer().openInventory(super.menu);
    }

    @Override
    public void interractInventory(InventoryClickEvent e) {
        ItemStack itemStack = e.getCurrentItem();
        switch (itemStack.getType()) {
            case ARROW:
                Scenario.setMenu(new MenuScenario());
                break;
            case GOLDEN_CARROT:
                if (e.isLeftClick()) {
                    Scenario.addLife();
                } else if (e.isRightClick()) {
                    Scenario.removeLife();
                }
                break;
            case SKULL_ITEM:
                if (e.isLeftClick()) {
                    Scenario.addNbrPlayer();
                } else if (e.isRightClick()) {
                    Scenario.removeNbrPlayer();
                }
                break;
            default:
                break;
        }
    }
}
