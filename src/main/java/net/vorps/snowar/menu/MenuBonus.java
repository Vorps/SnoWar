package net.vorps.snowar.menu;

import lombok.Getter;
import net.vorps.api.lang.Lang;
import net.vorps.api.menu.Item;
import net.vorps.api.menu.MenuRecursive;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.bonus.Bonus;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.scenario.Scenario;
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

    private @Getter PlayerData playerData;

    private MenuBonus(final PlayerData playerData, final ArrayList<Item> list) {
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU_BONUS_TITLE", playerData.getLang())), null, list, playerData.getLang(), 9, 0, SnoWar.getInstance());
        this.playerData = playerData;
        this.initMenu(playerData.getPlayer(), 1);
        playerData.getPlayer().openInventory(super.menu);
    }

    @Override
    public void initMenu(final Player player, final int page) {
        super.menu.clear();
        super.getPage(page);
        super.menu.setItem(menu.getSize() - 6, new Item(Material.SNOW_BALL).withName(Lang.getMessage("SNO_WAR.SCENARIO_SPEED_BONUS.LABEL", playerData.getLang())).withLore(new String[]{Lang.getMessage("SNO_WAR.SCENARIO_SPEED_BONUS.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, "" + Parameter.getTimeBonus()))}).get());
        super.menu.setItem(menu.getSize() - 4, new Item(351).withData((byte) 10).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", playerData.getLang())).withLore(new String[]{Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
        player.updateInventory();
    }

    public int getSize() {
        return menu.getSize();
    }

    public static void createMenu(PlayerData playerData) {
        ArrayList<Item> list = new ArrayList<>();
        Bonus.getBonusList().forEach((Bonus bonus) -> list.add(net.vorps.api.utils.Item.getItem(bonus.getIcon(), playerData.getLang()).withLore(new String[]{Lang.getMessage("SNO_WAR.SCENARIO_BONUS_LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, "" + bonus.getPercent()))})));
        playerData.getScenario().setMenu(new MenuBonus(playerData, list));
    }


    public void updateItem(String[] lore, int index) {
        ItemStack itemStack = super.menu.getItem(index);
        super.menu.setItem(index, new Item(itemStack.getType()).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        this.playerData.getPlayer().updateInventory();
    }

    @Override
    public void interractInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        switch (itemStack.getType()) {
            case SNOW_BALL:
                if (itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.SCENARIO_SPEED_BONUS.LABEL", this.playerData.getLang()))) {
                    if (e.isLeftClick()) this.addSpeedBonus();
                    else if (e.isRightClick()) this.removeSpeedBonus();
                }
                break;
            case ARROW:
                if (itemStack.getItemMeta().getDisplayName().equals(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", PlayerData.getPlayerData(player.getName()).getLang())))
                    playerData.getScenario().setMenu(new MenuScenario(this.playerData));
                break;
            case PAPER:
                initMenu(player, super.page + 1);
                break;
            case EMPTY_MAP:
                initMenu(player, super.page - 1);
                break;
            default:
                break;
        }
        if ((itemStack.getType().getId() == 351) && (itemStack.getData().getData() == 10)) {
            this.setBonus();
            playerData.getScenario().setMenu(new MenuScenario(this.playerData));
        } else {
            Bonus bonus = Bonus.getBonus(itemStack);
            if (bonus != null) {
                if (e.isLeftClick()) {
                    bonus.addPercent();
                    this.updateItemBonus(e.getSlot(), bonus);
                } else if (e.isRightClick()){
                    bonus.removePercent();
                    this.updateItemBonus(e.getSlot(), bonus);
                }
            }
        }
    }

    private void updateItemBonus(int place, Bonus bonus){
        this.playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_BONUS_LORE", this.playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+bonus.getPercent()))}, place);
    }

    private void addSpeedBonus() {
        if (Parameter.getTimeBonus() + 1 <= Scenario.getSpeedBonusMax()) {
            Parameter.setTimeBonus(Parameter.getTimeBonus() + 1);
            this.updateItemSpeedBonus();
        }
    }

    private void updateItemSpeedBonus() {
        this.updateItem(new String[]{Lang.getMessage("SNO_WAR.SCENARIO_SPEED_BONUS.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, "" + Parameter.getTimeBonus()))}, this.getSize() - 6);
    }

    private void setBonus(){
        Parameter.setBonus(!Parameter.isBonus());
        if(Parameter.isBonus())  playerData.getScenario().updateItem(5, new Item(80).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", playerData.getLang())}));
    }

    private void removeSpeedBonus(){
        if(Parameter.getTimeBonus()-1 >= Scenario.getSpeedBonusMin()){
            Parameter.setTimeBonus(Parameter.getTimeBonus()-1);
            updateItemSpeedBonus();
        }
    }
}
