package me.vorps.snowar.menu;

import lombok.Getter;
import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project RushVolcano Created by Vorps on 27/04/2016 at 17:23.
 */
public class MenuScenario extends Menu {

    private @Getter PlayerData playerData;

    public MenuScenario(PlayerData playerData) {
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU_SCENARIO_TITLE", playerData.getLang())), null);
        this.playerData = playerData;
        Date date = new Date(Parameter.getTimeGame()*1000);
        date.setHours(date.getHours()-1);
        menu.setItem(0, new Item(345).withName(Lang.getMessage("SNO_WAR.SCENARIO.TIME_GAME_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_GAME", playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("HH:mm:ss").format(date)))}).get());
        menu.setItem(1, new Item(347).withName(Lang.getMessage("SNO_WAR.SCENARIO.WEATHER_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.WEATHER", playerData.getLang())}).get());
        if(Parameter.isFall())menu.setItem(3, new Item(351).withData((byte)10).withName(Lang.getMessage("SNO_WAR.SCENARIO_FALL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
        else menu.setItem(3, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO_FALL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
        menu.setItem(4, new Item(397).withName(Lang.getMessage("SNO_WAR.SCENARIO.PLAYER_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.PLAYER.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Data.getNbPlayerMax()))}).get());
        if(Parameter.isBonus())menu.setItem(5, new Item(80).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", playerData.getLang())}).get());
        else menu.setItem(5, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
        menu.setItem(7, new Item(267).withName(Lang.getMessage("SNO_WAR.SCENARIO.DAMAGE_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.DAMAGE_LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getDamage()))}).get());
        if(Parameter.isCoolDownBallState())menu.setItem(8, new Item(349).withData((byte) 3).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", playerData.getLang())}).get());
        else menu.setItem(8, new Item(351).withData((byte) 8).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", playerData.getLang())}).get());
        menu.setItem(13, new Item(Material.NETHER_STAR).withName(Lang.getMessage("SNO_WAR.SCENARIO.START_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.START_LORE", playerData.getLang())}).get());
        menu.setItem(9, new Item(Material.ARROW).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", playerData.getLang())).get());
        playerData.getPlayer().openInventory(menu);
    }

    public void setItem(int i){
        menu.getItem(i).setAmount(menu.getItem(i).getAmount()+1);
    }

    public void updateItem(int index ,Item items){
        menu.setItem(index, items.get());
        playerData.getPlayer().updateInventory();
    }

    public void updateItem(String[] lore, int index){
        ItemStack itemStack = menu.getItem(index);
        menu.setItem(index, new Item(itemStack.getType()).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        playerData.getPlayer().updateInventory();
    }

    public void updateItem(byte data, String[] lore, int index){
        ItemStack itemStack = menu.getItem(index);
        menu.setItem(index, new Item(itemStack.getType()).withData(data).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        playerData.getPlayer().updateInventory();
    }

    @Override
    public void interractInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
        switch (itemStack.getType()) {
            case NETHER_STAR:
                // TODO: 30/07/2016 Ouverture server
                break;
            case ARROW:
                player.closeInventory();
                break;
            case SNOW_BLOCK:
                MenuBonus.createMenu(playerData);
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
                if (e.isLeftClick()) {
                    Scenario.addNbrPlayer();
                } else if (e.isRightClick()) {
                    Scenario.removeNbrPlayer();
                }
                break;
            default:
                if(itemStack.getType().getId() == 347){
                    Scenario.setMenu(new MenuTimes(playerData));
                } else if((itemStack.getType().getId() == 351) && (itemStack.getData().getData() == 8 || itemStack.getData().getData() == 10)){
                    if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO_FALL", playerData.getLang()))){
                        Scenario.setFall();
                    } else if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", playerData.getLang()))){
                        Scenario.setBonus();
                    } else if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", playerData.getLang()))){
                        Scenario.setCoolDown();
                    }
                } else if(itemStack.getType().getId() == 349){
                    Scenario.setMenu(new MenuCoolDown(playerData));
                }
                break;
        }
    }
}
