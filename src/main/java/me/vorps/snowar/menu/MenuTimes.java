package me.vorps.snowar.menu;

import lombok.Getter;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scenario.Scenario;
import me.vorps.snowar.utils.Hour;
import me.vorps.snowar.utils.Weather;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 31/07/2016 at 01:30.
 */
public class MenuTimes extends Menu {

    private @Getter PlayerData playerData;

    public MenuTimes(PlayerData playerData){
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU.WEATHER.TITLE", playerData.getLang())), null);
        this.playerData = playerData;
        menu.setItem(2, new Item(347).withName(Lang.getMessage("SNO_WAR.SCENARIO.HOUR.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.HOUR.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, Hour.getHour(Parameter.getHour()).getLabel().get(PlayerData.getPlayerData(playerData.getPlayer().getName()).getLang())))}).get());
        if(Parameter.isCycle()) menu.setItem(4, new Item(351).withData((byte) 10).withName(Lang.getMessage("SNO_WAR.SCENARIO.CYCLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
        else menu.setItem(4, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.CYCLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
        menu.setItem(6, new Item(151).withName(Lang.getMessage("SNO_WAR.SCENARIO.WEATHER.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.WEATHER.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Weather.values()[Scenario.getWeather()].getLabel(playerData.getLang())))}).get());
        super.menu.setItem(9, new Item(Material.ARROW).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", playerData.getLang())).get());
        playerData.getPlayer().openInventory(super.menu);
    }

    public void updateItem(byte data, String[] lore, int index){
        ItemStack itemStack = menu.getItem(index);
        menu.setItem(index, new Item(itemStack.getType()).withData(data).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        playerData.getPlayer().updateInventory();
    }

    public void updateItem(String[] lore, int index){
        ItemStack itemStack = menu.getItem(index);
        menu.setItem(index, new Item(itemStack.getType()).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        playerData.getPlayer().updateInventory();
    }

    @Override
    public void interractInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
        switch (itemStack.getType()) {
            case ARROW:
                Scenario.setMenu(new MenuScenario(playerData));
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
