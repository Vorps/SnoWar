package me.vorps.snowar.menu;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scenario.Scenario;
import me.vorps.snowar.utils.Hour;
import me.vorps.snowar.utils.Weather;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.menu.Item;
import me.vorps.syluriapi.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 31/07/2016 at 01:30.
 */
public class MenuTimes extends Menu {

    public MenuTimes(final PlayerData playerData){
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU.WEATHER.TITLE", playerData.getLang())), null);
        super.menu.setItem(2, new Item(347).withName(Lang.getMessage("SNO_WAR.SCENARIO.HOUR.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.HOUR.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, Hour.getHour(Parameter.getHour()).getLabel().get(PlayerData.getPlayerData(playerData.getPlayer().getName()).getLang())))}).get());
        if(Parameter.isCycle()) menu.setItem(4, new Item(351).withData((byte) 10).withName(Lang.getMessage("SNO_WAR.SCENARIO.CYCLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
        else super.menu.setItem(4, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.CYCLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
        super.menu.setItem(6, new Item(151).withName(Lang.getMessage("SNO_WAR.SCENARIO.WEATHER.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.WEATHER.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Weather.values()[Scenario.getWeather()].getLabel(playerData.getLang())))}).get());
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
            default:
                if(itemStack.getType().getId() == 347) {
                    Scenario.addHourFunction();
                } else  if((itemStack.getType().getId() == 351)){
                    Scenario.setCycle();
                } else if(itemStack.getType().getId() == 151){
                    Scenario.setWeather();
                }
                break;
        }
    }
}
