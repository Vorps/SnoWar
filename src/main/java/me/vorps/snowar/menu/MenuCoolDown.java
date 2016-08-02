package me.vorps.snowar.menu;

import me.vorps.snowar.PlayerData;
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

    Player player;

    public MenuCoolDown(PlayerData playerData){
        super(null, Bukkit.createInventory(null, 18, "§7Menu Cooldown"), null);
        this.player = playerData.getPlayer();
        super.menu.setItem(9, new Item(Material.ARROW).withName("§6Quitter le menu").withLore(new String[] {"§7Retour au menu Scenario"}).get());
        menu.setItem(1, new Item(Material.COMPASS).withName("§6Temps de cooldown").withLore(new String[] {"§a"+ Parameter.getCooldownBall()}).get());
        menu.setItem(3, new Item(Material.SNOW_BALL).withName("§6Nombre de balles").withLore(new String[] {"§a"+Parameter.getNbrBall()}).get());
        menu.setItem(5, new Item(347).withName("§6Temps entre les balles").withLore(new String[] {"§a"+Parameter.getTimeBall()}).get());
        menu.setItem(7, new Item(351).withData((byte) 10).withName("§6CoolDown Ball").withLore(new String[] {"§aActivé"}).get());
        playerData.getPlayer().openInventory(super.menu);
    }

    public void updateItem(String[] lore, int index){
        ItemStack itemStack = menu.getItem(index);
        menu.setItem(index, new Item(itemStack.getType()).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        player.updateInventory();
    }

    @Override
    public void interractInventory(InventoryClickEvent e) {
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
                Scenario.setMenu(new MenuScenario(player));
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
                    Scenario.setMenu(new MenuScenario(player));
                }
                break;
        }
    }
}
