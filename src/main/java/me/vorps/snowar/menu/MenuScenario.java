package me.vorps.snowar.menu;

import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.objects.MapParameter;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scenario.Scenario;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.menu.Item;
import me.vorps.syluriapi.menu.Menu;
import me.vorps.syluriapi.utils.Info;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project RushVolcano Created by Vorps on 27/04/2016 at 17:23.
 */
public class MenuScenario extends Menu {

    public MenuScenario() {
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU_SCENARIO_TITLE", Scenario.getPlayerData().getLang())), null);
        Date date = new Date(Parameter.getTimeGame()*1000);
        date.setHours(date.getHours()-1);
        super.menu.setItem(0, new Item(345).withName(Lang.getMessage("SNO_WAR.SCENARIO.TIME_GAME_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_GAME", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("HH:mm:ss").format(date)))}).get());
        super.menu.setItem(1, new Item(347).withName(Lang.getMessage("SNO_WAR.SCENARIO.WEATHER_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.WEATHER", Scenario.getPlayerData().getLang())}).get());
        if(Parameter.isFall()) super.menu.setItem(3, new Item(351).withData((byte)10).withName(Lang.getMessage("SNO_WAR.SCENARIO_FALL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", Scenario.getPlayerData().getLang())}).get());
        else super.menu.setItem(3, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO_FALL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", Scenario.getPlayerData().getLang())}).get());
        super.menu.setItem(4, new Item(Scenario.getPlayerData().getPlayer().getName()).withName(Lang.getMessage("SNO_WAR.SCENARIO.MENU_PLAYER_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.MENU_PLAYER_LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Data.getNbPlayerMax()))}).get());
        if(Parameter.isBonus())menu.setItem(5, new Item(80).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", Scenario.getPlayerData().getLang())}).get());
        else super.menu.setItem(5, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", Scenario.getPlayerData().getLang())}).get());
        super.menu.setItem(7, new Item(267).withName(Lang.getMessage("SNO_WAR.SCENARIO.DAMAGE_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.DAMAGE_LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getDamage()))}).get());
        if(Parameter.isCoolDownBallState()) super.menu.setItem(8, new Item(349).withData((byte) 3).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", Scenario.getPlayerData().getLang())}).get());
        else super.menu.setItem(8, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", Scenario.getPlayerData().getLang())}).get());
        super.menu.setItem(13, new Item(Material.NETHER_STAR).withName(Lang.getMessage("SNO_WAR.SCENARIO.START_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.START_LORE", Scenario.getPlayerData().getLang())}).get());
        super.menu.setItem(9, new Item(Material.ARROW).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", Scenario.getPlayerData().getLang())).get());
        Scenario.getPlayerData().getPlayer().openInventory(super.menu);
    }

    @Override
    public void interractInventory(InventoryClickEvent e) {
        ItemStack itemStack = e.getCurrentItem();
        switch (itemStack.getType()) {
            case NETHER_STAR:
                Info.setInfo(true, false, MapParameter.getName(), true);
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage(Lang.getMessage("SNO_WAR.SCENARIO.START", PlayerData.getPlayerData(e.getWhoClicked().getName()).getLang()));
                break;
            case ARROW:
                Scenario.closeScenario();
                break;
            case SNOW_BLOCK:
                MenuBonus.createMenu(Scenario.getPlayerData());
                break;
            case IRON_SWORD:
                if (e.isLeftClick()) {
                    Scenario.addDamage();
                } else if (e.isRightClick()) {
                    Scenario.removeDamage();
                }
                break;
            case COMPASS:
                if (e.isLeftClick()) {
                    Scenario.addTime();
                } else if (e.isRightClick()) {
                    Scenario.removeTime();
                }
                break;
            case SKULL_ITEM:
                Scenario.setMenu(new MenuPlayers(Scenario.getPlayerData()));
                break;
            default:
                if(itemStack.getType().getId() == 347){
                    Scenario.setMenu(new MenuTimes(Scenario.getPlayerData()));
                } else if((itemStack.getType().getId() == 351) && (itemStack.getData().getData() == 8 || itemStack.getData().getData() == 10)){
                    if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO_FALL", Scenario.getPlayerData().getLang()))){
                        Scenario.setFall();
                    } else if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", Scenario.getPlayerData().getLang()))){
                        Scenario.setBonus();
                    } else if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", Scenario.getPlayerData().getLang()))){
                        Scenario.setCoolDown();
                    }
                } else if(itemStack.getType().getId() == 349){
                    Scenario.setMenu(new MenuCoolDown(Scenario.getPlayerData()));
                }
                break;
        }
    }
}
