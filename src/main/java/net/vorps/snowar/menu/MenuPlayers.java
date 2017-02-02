package net.vorps.snowar.menu;

import net.vorps.api.lang.Lang;
import net.vorps.api.menu.Item;
import net.vorps.api.menu.Menu;
import net.vorps.snowar.Data;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 06/08/2016 at 20:51.
 */
public class MenuPlayers extends Menu {

    private PlayerData playerData;

    public MenuPlayers(PlayerData playerData){
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU_PLAYER_TITLE", playerData.getLang())), null, SnoWar.getInstance());
        this.playerData = playerData;
        super.menu.setItem(3, new Item(397).withName(Lang.getMessage("SNO_WAR.SCENARIO.PLAYER_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.PLAYER.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+ Data.getNbPlayerMax()))}).get());
        super.menu.setItem(5, new Item(Material.GOLDEN_CARROT).withName(Lang.getMessage("SNO_WAR.SCENARIO.LIFE_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.PLAYER.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+ Parameter.getLife()))}).get());
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
            case GOLDEN_CARROT:
                if (e.isLeftClick()) this.addLife();
                else if (e.isRightClick()) this.removeLife();
                break;
            case SKULL_ITEM:
                if (e.isLeftClick()) this.addNbrPlayer();
                else if (e.isRightClick()) this.removeNbrPlayer();
                break;
            default:
                break;
        }
    }

    private void addLife(){
        if(Parameter.getLife()+1 <= Scenario.getLifeMax()){
            Parameter.setLife(Parameter.getLife()+1);
        }
        this.updateLife();
    }

    private void removeLife(){
        if(Parameter.getLife()-1 >= Scenario.getLifeMin()){
            Parameter.setLife(Parameter.getLife()-1);
        }
        this.updateLife();
    }

    private void updateLife(){
        this.playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.LIFE.LORE", this.playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getLife()))}, 5);
    }

    private void addNbrPlayer(){
        if(Data.getNbPlayerMax()+1 <= Scenario.getNbPlayerMax()){
            Data.setNbPlayerMax(Data.getNbPlayerMax()+1);
            this.updateItemNbrPlayer();
        }
    }

    private void removeNbrPlayer(){
        if(Data.getNbPlayerMax()-1 >= Scenario.getNbPlayerMin()){
            Data.setNbPlayerMax(Data.getNbPlayerMax()-1);
            this.updateItemNbrPlayer();
        }
    }

    private void updateItemNbrPlayer(){
        this.playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.PLAYER.LORE", this.playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Data.getNbPlayerMax()))}, 3);
    }
}
