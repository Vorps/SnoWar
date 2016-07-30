package me.vorps.snowar.menu;

import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
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

    private Player player;

    public MenuScenario(Player player) {
        super(null, Bukkit.createInventory(null, 18, "§7Menu scenario"), null);
        this.player = player;
        Date date = new Date(Parameter.getTimeGame()*1000);
        date.setHours(date.getHours()-1);
        menu.setItem(0, new Item(Material.COMPASS).withName("§6Temps de jeu").withLore(new String[] {"§6Temps : §a"+new SimpleDateFormat("HH:mm:ss").format(date)}).get());
        Scenario.Hour hour = Scenario.Hour.getHour(Parameter.getHour());
        if(hour != null){
            menu.setItem(1, new Item(347).withName("§6Heure").withLore(new String[] {"§7"+hour.getLabel().get(PlayerData.getPlayerData(player.getName()).getLang())}).get());
        } else {
            menu.setItem(1, new Item(347).withName("§6Heure").withLore(new String[] {"§7Jour"}).get());
        }
        menu.setItem(2, new Item(Material.SKULL_ITEM).withName("§6Nombre de joueur").withLore(new String[] {"§a"+ Data.getNbPlayerMax()}).get());
        if(Parameter.isFall()){
            menu.setItem(3, new Item(351).withData((byte) 10).withName("§6Dégat de chute").withLore(new String[] {"§aActivé"}).get());
        } else {
            menu.setItem(3, new Item(351).withData((byte) 8).withName("§6Dégat de chute").withLore(new String[] {"§cDésactivé"}).get());
        }
        if(Parameter.isCycle()){
            menu.setItem(4, new Item(351).withData((byte) 10).withName("§6Cycle naturel").withLore(new String[] {"§aActivé"}).get());
        } else {
            menu.setItem(4, new Item(351).withData((byte) 8).withName("§6Cycle naturel").withLore(new String[] {"§cDésactivé"}).get());
        }

        if(Parameter.isBonus()){
            menu.setItem(5, new Item(Material.SNOW_BLOCK).withName("§6Bonus").withLore(new String[] {"§7Configurer les bonus du jeu"}).get());
        } else {
            menu.setItem(5, new Item(351).withData((byte) 8).withName("§6Bonus").withLore(new String[] {"§cDésactivé"}).get());
        }
        menu.setItem(6, new Item(Material.IRON_SWORD).withName("§6Damage").withLore(new String[] {"§a"+Parameter.getDamage()}).get());
        menu.setItem(8, new Item(349).withData((byte) 3).withName("§6Cooldown").get());
        menu.setItem(13, new Item(Material.NETHER_STAR).withName("§6Démarrer").get());
        menu.setItem(9, new Item(Material.ARROW).withName("§6Quitter le menu").withLore(new String[] {"§7Retour au jeu"}).get());
        player.openInventory(menu);
    }

    public void setItem(int i){
        menu.getItem(i).setAmount(menu.getItem(i).getAmount()+1);
    }

    public void updateItem(int index ,Item items){
        menu.setItem(index, items.get());
        player.updateInventory();
    }

    public void updateItem(String[] lore, int index){
        ItemStack itemStack = menu.getItem(index);
        menu.setItem(index, new Item(itemStack.getType()).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        player.updateInventory();
    }

    public void updateItem(byte data, String[] lore, int index){
        ItemStack itemStack = menu.getItem(index);
        menu.setItem(index, new Item(itemStack.getType()).withData(data).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        player.updateInventory();
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
                    Scenario.addHour(playerData.getLang());
                } else if((itemStack.getType().getId() == 351) && (itemStack.getData().getData() == 8 || itemStack.getData().getData() == 10)){
                    if(itemStack.getItemMeta().getDisplayName().equals("§6Dégat de chute")){
                        Scenario.setFall();
                    } else if(itemStack.getItemMeta().getDisplayName().equals("§6Bonus")){
                        Scenario.setBonus();
                    } else {
                        Scenario.setCycle();
                    }
                }
                break;
        }
    }
}
