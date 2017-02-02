package net.vorps.snowar.menu;

import net.vorps.api.lang.Lang;
import net.vorps.api.menu.Item;
import net.vorps.api.menu.Menu;
import net.vorps.api.utils.Info;
import net.vorps.snowar.Data;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.objects.MapParameter;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.scenario.Scenario;
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

    private PlayerData playerData;
    public MenuScenario(PlayerData playerData) {
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU_SCENARIO_TITLE", playerData.getLang())), null, SnoWar.getInstance());
        this.playerData = playerData;
        Date date = new Date(Parameter.getTimeGame()*1000);
        date.setHours(date.getHours()-1);
        super.menu.setItem(0, new Item(345).withName(Lang.getMessage("SNO_WAR.SCENARIO.TIME_GAME_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_GAME", playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("HH:mm:ss").format(date)))}).get());
        super.menu.setItem(1, new Item(347).withName(Lang.getMessage("SNO_WAR.SCENARIO.WEATHER_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.WEATHER", playerData.getLang())}).get());
        if(Parameter.isFall()) super.menu.setItem(3, new Item(351).withData((byte)10).withName(Lang.getMessage("SNO_WAR.SCENARIO_FALL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
        else super.menu.setItem(3, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO_FALL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
        super.menu.setItem(4, new Item(playerData.getPlayer().getName()).withName(Lang.getMessage("SNO_WAR.SCENARIO.MENU_PLAYER_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.MENU_PLAYER_LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Data.getNbPlayerMax()))}).get());
        if(Parameter.isBonus())menu.setItem(5, new Item(80).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", playerData.getLang())}).get());
        else super.menu.setItem(5, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
        super.menu.setItem(7, new Item(267).withName(Lang.getMessage("SNO_WAR.SCENARIO.DAMAGE_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.DAMAGE_LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getDamage()))}).get());
        if(Parameter.isCoolDownBallState()) super.menu.setItem(8, new Item(349).withData((byte) 3).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", playerData.getLang())}).get());
        else super.menu.setItem(8, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
        super.menu.setItem(13, new Item(Material.NETHER_STAR).withName(Lang.getMessage("SNO_WAR.SCENARIO.START_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.START_LORE", playerData.getLang())}).get());
        super.menu.setItem(9, new Item(Material.ARROW).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", playerData.getLang())).get());
        playerData.getPlayer().openInventory(super.menu);
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
                this.playerData.getScenario().closeScenario();
                break;
            case SNOW_BLOCK:
                MenuBonus.createMenu( this.playerData);
                break;
            case IRON_SWORD:
                if (e.isLeftClick()) this.addDamage();
                else if (e.isRightClick()) this.removeDamage();
                break;
            case COMPASS:
                if (e.isLeftClick()) this.addTime();
                else if (e.isRightClick()) this.removeTime();
                break;
            case SKULL_ITEM:
                this.playerData.getScenario().setMenu(new MenuPlayers( this.playerData));
                break;
            default:
                if(itemStack.getType().getId() == 347) this.playerData.getScenario().setMenu(new MenuTimes( this.playerData));
                else if((itemStack.getType().getId() == 351) && (itemStack.getData().getData() == 8 || itemStack.getData().getData() == 10))
                    if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO_FALL",  this.playerData.getLang()))) this.setFall();
                    else if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL",  this.playerData.getLang()))) this.setBonus();
                    else if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL",  this.playerData.getLang()))) this.setCoolDown();
                else if(itemStack.getType().getId() == 349) this.playerData.getScenario().setMenu(new MenuCoolDown( this.playerData));
                break;
        }
    }

    public void addDamage(){
        if(Parameter.getDamage()+1 <= Scenario.getDamageMax()){
            Parameter.setDamage(Parameter.getDamage()+1);
            this.updateItemDamage();
        }
    }


    public void setCoolDown(){
        System.out.println("ok");
        Parameter.setCoolDownBallState(!Parameter.isCoolDownBallState());
        if(Parameter.isCoolDownBallState()) this.playerData.getScenario().updateItem(8, new Item(349).withData((byte) 3).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", playerData.getLang())}));
        else this.playerData.getScenario().updateItem(8, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}));
    }


    public void removeDamage(){
        if(Parameter.getDamage()-1 >= Scenario.getDamageMin()){
            Parameter.setDamage(Parameter.getDamage()-1);
            this.updateItemDamage();
        }
    }

    private void updateItemDamage(){
        this.playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.DAMAGE_LORE", this.playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getDamage()))}, 7);
    }

    private void addTime(){
        if(Parameter.getTimeGame()+60 <= Scenario.getTimeMax()){
            Parameter.setTimeGame(Parameter.getTimeGame()+60);
            updateItemTime();
        }
    }


    private void removeTime(){
        if(Parameter.getTimeGame()-60 >= Scenario.getTimeMin()){
            Parameter.setTimeGame(Parameter.getTimeGame()-60);
            updateItemTime();
        }
    }

    public void setFall(){
        Parameter.setFall(!Parameter.isFall());
        if(Parameter.isFall()) this.playerData.getScenario().updateItem((byte) 10,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", this.playerData.getLang())}, 3);
        else this.playerData.getScenario().updateItem((byte) 8,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", this.playerData.getLang())}, 3);

    }

    private void updateItemTime(){
        Date date = new Date(Parameter.getTimeGame()*1000);
        date.setHours(date.getHours()-1);
        this.playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_GAME", this.playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("HH:mm:ss").format(date)))}, 0);
    }

    public void setBonus(){
        Parameter.setBonus(!Parameter.isBonus());
        if(Parameter.isBonus())  this.playerData.getScenario().updateItem(5, new Item(80).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", playerData.getLang())}));
    }
}
