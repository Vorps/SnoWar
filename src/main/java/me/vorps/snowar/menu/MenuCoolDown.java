package me.vorps.snowar.menu;

import lombok.Getter;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 30/07/2016 at 20:07.
 */
public class MenuCoolDown extends Menu {

    private @Getter PlayerData playerData;

    public MenuCoolDown(PlayerData playerData){
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU.COOLDOWN.TITLE", playerData.getLang())), null);
        this.playerData = playerData;
        menu.setItem(1, new Item(Material.COMPASS).withName(Lang.getMessage("SNO_WAR.SCENARIO.TIME_COOLDOWN.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_COOLDOWN.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getCooldownBall()))}).get());
        menu.setItem(3, new Item(Material.SNOW_BALL).withName(Lang.getMessage("SNO_WAR.SCENARIO.BALL.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.BALL.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, "§a"+Parameter.getNbrBall()))}).get());
        menu.setItem(5, new Item(347).withName(Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBall()))}).get());
        menu.setItem(7, new Item(351).withData((byte) 10).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
        menu.setItem(9, new Item(Material.ARROW).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", playerData.getLang())).get());
        playerData.getPlayer().openInventory(super.menu);
    }

    public void updateItem(String[] lore, int index){
        ItemStack itemStack = menu.getItem(index);
        menu.setItem(index, new Item(itemStack.getType()).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        playerData.getPlayer().updateInventory();
    }

    @Override
    public void interractInventory(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
        switch (itemStack.getType()) {
            case COMPASS:
                if (e.isLeftClick()) {
                    Scenario.addTimeCoolDown();
                } else if (e.isRightClick()) {
                    Scenario.removeTimeCoolDown();
                }
                break;
            case SNOW_BALL:
                if (e.isLeftClick()) {
                    Scenario.addBallCoolDown();
                } else if (e.isRightClick()) {
                    Scenario.removeBallCoolDown();
                }
                break;
            case ARROW:
                Scenario.setMenu(new MenuScenario(playerData));
                break;
            default:
                if(itemStack.getType().getId() == 347){
                    if (e.isLeftClick()) {
                        Scenario.addTimeBall();
                    } else if (e.isRightClick()) {
                        Scenario.removeTimeBall();
                    }
                } else if((itemStack.getType().getId() == 351)){
                    Scenario.setCoolDown();
                    Scenario.setMenu(new MenuScenario(playerData));
                }
                break;
        }
    }
}
