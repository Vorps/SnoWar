package net.vorps.snowar.utils;

import net.vorps.snowar.PlayerData;
import net.vorps.snowar.menu.MenuHelp;
import net.vorps.snowar.scenario.Scenario;
import net.vorps.api.lang.Lang;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 29/08/2016 at 12:58.
 */
public class Event {

    public static void interractItem(ItemStack item, PlayerData playerData){
        switch (item.getType()) {
            case BED:
                net.vorps.api.players.PlayerData.lobby(playerData.getPlayer().getUniqueId());
                break;
            case REDSTONE_COMPARATOR:
                playerData.setScenario(new Scenario(playerData));
                break;
            case BOOK:
                MenuHelp.createMenu(playerData);
                break;
            default:
                if(item.getType().getId() == 160){
                    if(item.getData().getData() == 5){
                        playerData.setEffect(false);
                        playerData.getPlayer().getInventory().setItem(4, new net.vorps.api.menu.Item(160).withData((byte) 14).withName(Lang.getMessage("SNO_WAR.ITEM.EFFECT.LABEL", playerData.getLang())+" "+Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
                    } else {
                        playerData.setEffect(true);
                        playerData.getPlayer().getInventory().setItem(4, new net.vorps.api.menu.Item(160).withData((byte) 5).withName(Lang.getMessage("SNO_WAR.ITEM.EFFECT.LABEL", playerData.getLang())+" "+Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
                    }
                }
                break;
        }
    }
}
