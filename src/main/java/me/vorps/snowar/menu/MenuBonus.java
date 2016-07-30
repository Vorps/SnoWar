package me.vorps.snowar.menu;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.bonus.Bonus;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Project SnoWar Created by Vorps on 30/07/2016 at 02:37.
 */
public class MenuBonus extends MenuRecursive {

    private Player player;

    private MenuBonus(PlayerData playerData, ArrayList<Item> list){
        super(null,  Bukkit.createInventory(null, 18, "§7Menu Bonus"), null, list, playerData.getLang(), 9, 0);
        this.player = playerData.getPlayer();
        initMenu(playerData.getPlayer(), 1);
        playerData.getPlayer().openInventory(super.menu);
    }


    @Override
    public void initMenu(Player player, int page){
        menu.clear();
        getPage(page);
        menu.setItem(menu.getSize()-6, new Item(Material.SNOW_BALL).withName("§6Speed Bonus").withLore(new String[] {"§a"+Parameter.getTimeBonus()}).get());
        menu.setItem(menu.getSize()-4, new Item(351).withData((byte) 10).withName("§6Bonus").withLore(new String[] {"§aActivé"}).get());
        player.updateInventory();
    }

    public int getSize(){
        return menu.getSize();
    }

    public static void createMenu(PlayerData playerData){
        ArrayList<Item> list = new ArrayList<>();
        Bonus.getBonusList().forEach((Bonus bonus) -> {
            list.add(me.vorps.snowar.utils.Item.getItem(bonus.getIcon(), playerData.getLang()).withLore(new String[] {"§7Percent : §a"+bonus.getPercent() +" %"}));
        });
        Scenario.setMenu(new MenuBonus(playerData, list));
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
        switch(itemStack.getType()) {
            case SNOW_BALL:
                if(itemStack.getItemMeta().getDisplayName().equals("§6Speed Bonus")){
                    if (e.isLeftClick()) {
                        Scenario.addSpeedBonus();
                    } else if (e.isRightClick()) {
                        Scenario.removeSpeedBonus();
                    }
                }
                break;
            case ARROW:
                if(itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", PlayerData.getPlayerData(player.getName()).getLang()))){
                    Scenario.setMenu(new MenuScenario(player));
                }
                break;
            case PAPER:
                initMenu(player, page+1);
                break;
            case EMPTY_MAP:
                initMenu(player, page-1);
                break;
            default:
                break;
        }
        if((itemStack.getType().getId() == 351) && (itemStack.getData().getData() == 10)){
            Scenario.setBonus();
            Scenario.setMenu(new MenuScenario(player));
        } else {
            Bonus bonus = Bonus.getBonus(itemStack);
            if(bonus != null){
                if (e.isLeftClick()) {
                    bonus.addPercent(e.getSlot());
                } else if (e.isRightClick()) {
                    bonus.removePercent(e.getSlot());
                }
            }
        }
    }
}
