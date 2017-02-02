package net.vorps.snowar.menu;

import net.vorps.api.lang.Lang;
import net.vorps.api.menu.Item;
import net.vorps.api.menu.Menu;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.scenario.Scenario;
import net.vorps.snowar.utils.Hour;
import net.vorps.snowar.utils.Weather;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 31/07/2016 at 01:30.
 */
public class MenuTimes extends Menu {

    private PlayerData playerData;
    public MenuTimes(final PlayerData playerData){
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU.WEATHER.TITLE", playerData.getLang())), null, SnoWar.getInstance());
        this.playerData = playerData;
        super.menu.setItem(2, new Item(347).withName(Lang.getMessage("SNO_WAR.SCENARIO.HOUR.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.HOUR.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, Parameter.getHour().getLabel().get(PlayerData.getPlayerData(playerData.getPlayer().getName()).getLang())))}).get());
        if(Parameter.isCycle()) menu.setItem(4, new Item(351).withData((byte) 10).withName(Lang.getMessage("SNO_WAR.SCENARIO.CYCLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
        else super.menu.setItem(4, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.CYCLE", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
        super.menu.setItem(6, new Item(151).withName(Lang.getMessage("SNO_WAR.SCENARIO.WEATHER.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.WEATHER.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+ Parameter.getWeather().getLabel(playerData.getLang())))}).get());
        super.menu.setItem(9, new Item(Material.ARROW).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", playerData.getLang())).get());
        playerData.getPlayer().openInventory(super.menu);
    }

    @Override
    public void interractInventory(InventoryClickEvent e) {
        ItemStack itemStack = e.getCurrentItem();
        switch (itemStack.getType()) {
            case ARROW:
                this.playerData.getScenario().setMenu(new MenuScenario(this.playerData));
                break;
            default:
                if(itemStack.getType().getId() == 347) this.addHourFunction();
                else  if((itemStack.getType().getId() == 351)) this.setCycle();
                else if(itemStack.getType().getId() == 151) this.setWeather();
                break;
        }
    }

    public void setCycle(){
        Parameter.setCycle(!Parameter.isCycle());
        Bukkit.getWorlds().get(0).setGameRuleValue("doDaylightCycle", ""+Parameter.isCycle());
        if(Parameter.isCycle()) this.playerData.getScenario().updateItem((byte) 10,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", this.playerData.getLang())}, 4);
        else this.playerData.getScenario().updateItem((byte) 8,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", this.playerData.getLang())}, 4);
    }

    public void addHourFunction(){
        Hour.addHour();
        this.playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.HOUR.LORE", this.playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, Parameter.getHour().getLabel().get(this.playerData.getLang())))}, 2);
    }

    public void setWeather(){
        Weather.addWeather();
        this.playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.WEATHER.LORE", this.playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, Parameter.getWeather().getLabel(this.playerData.getLang())))}, 6);
    }


}
