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
 * Project SnoWar Created by Vorps on 31/07/2016 at 01:30.
 */
public class MenuTimes extends Menu {

    Player player;

    public MenuTimes(PlayerData playerData){
        super(null, Bukkit.createInventory(null, 18, "§7Menu Temps"), null);
        this.player = playerData.getPlayer();
        Scenario.Hour hour = Scenario.Hour.getHour(Parameter.getHour());
        if(hour != null){
            menu.setItem(1, new Item(347).withName("§6Heure").withLore(new String[] {"§7"+hour.getLabel().get(PlayerData.getPlayerData(playerData.getPlayer().getName()).getLang())}).get());
        } else {
            menu.setItem(1, new Item(347).withName("§6Heure").withLore(new String[] {"§7Jour"}).get());
        }
        if(Parameter.isCycle()){
            menu.setItem(4, new Item(351).withData((byte) 10).withName("§6Cycle naturel").withLore(new String[] {"§aActivé"}).get());
        } else {
            menu.setItem(4, new Item(351).withData((byte) 8).withName("§6Cycle naturel").withLore(new String[] {"§cDésactivé"}).get());
        }
        menu.setItem(7, new Item(151).withName("§6Météo").withLore(new String[] {}).get());

        super.menu.setItem(9, new Item(Material.ARROW).withName("§6Quitter le menu").withLore(new String[] {"§7Retour au jeu"}).get());
        playerData.getPlayer().openInventory(super.menu);
    }

    public void updateItem(byte data, String[] lore, int index){
        ItemStack itemStack = menu.getItem(index);
        menu.setItem(index, new Item(itemStack.getType()).withData(data).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        player.updateInventory();
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
            case ARROW:
                Scenario.setMenu(new MenuScenario(player));
                break;
            default:
                if(itemStack.getType().getId() == 347) {
                    Scenario.addHour(playerData.getLang());
                } else  if((itemStack.getType().getId() == 351) && (itemStack.getData().getData() == 8 || itemStack.getData().getData() == 10)){
                    Scenario.setCycle();
                }
                break;
        }
    }
}
