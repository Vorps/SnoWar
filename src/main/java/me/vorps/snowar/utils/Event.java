package me.vorps.snowar.utils;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.menu.MenuHelp;
import me.vorps.snowar.scenario.Scenario;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.players.Lobby;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 29/08/2016 at 12:58.
 */
public class Event {

    public static void interractItem(ItemStack item, PlayerData playerData){
        switch (item.getType()) {
            case BED:
                Lobby.lobby(playerData.getPlayer());
                break;
            case REDSTONE_COMPARATOR:
                Scenario.openScenario(playerData);
                break;
            case BOOK:
                MenuHelp.createMenu(playerData);
                break;
            default:
                if(item.getType().getId() == 160){
                    if(item.getData().getData() == 5){
                        playerData.setEffect(false);
                        playerData.getPlayer().getInventory().setItem(4, new me.vorps.syluriapi.menu.Item(160).withData((byte) 14).withName(Lang.getMessage("SNO_WAR.ITEM.EFFECT.LABEL", playerData.getLang())+" "+Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
                    } else {
                        playerData.setEffect(true);
                        playerData.getPlayer().getInventory().setItem(4, new me.vorps.syluriapi.menu.Item(160).withData((byte) 5).withName(Lang.getMessage("SNO_WAR.ITEM.EFFECT.LABEL", playerData.getLang())+" "+Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
                    }
                }
                break;
        }
    }
}
