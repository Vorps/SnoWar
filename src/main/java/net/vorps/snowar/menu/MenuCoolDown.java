package net.vorps.snowar.menu;

import net.vorps.api.lang.Lang;
import net.vorps.api.menu.Item;
import net.vorps.api.menu.Menu;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 30/07/2016 at 20:07.
 */
public class MenuCoolDown extends Menu {


    private PlayerData playerData;

    public MenuCoolDown(final PlayerData playerData){
        super(null, Bukkit.createInventory(null, 18, Lang.getMessage("SNO_WAR.MENU.COOLDOWN.TITLE", playerData.getLang())), null, SnoWar.getInstance());
        this.playerData = playerData;
        super.menu.setItem(1, new Item(Material.COMPASS).withName(Lang.getMessage("SNO_WAR.SCENARIO.TIME_COOLDOWN.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_COOLDOWN.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getCooldownBall()))}).get());
        super.menu.setItem(3, new Item(Material.SNOW_BALL).withName(Lang.getMessage("SNO_WAR.SCENARIO.BALL.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.BALL.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, "§a"+Parameter.getNbrBall()))}).get());
        super.menu.setItem(5, new Item(347).withName(Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LORE", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBall()))}).get());
        super.menu.setItem(7, new Item(351).withData((byte) 10).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", playerData.getLang())}).get());
        super.menu.setItem(9, new Item(Material.ARROW).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", playerData.getLang())).get());
        playerData.getPlayer().openInventory(super.menu);
    }

    @Override
    public void interractInventory(InventoryClickEvent e){
        ItemStack itemStack = e.getCurrentItem();
        switch (itemStack.getType()) {
            case COMPASS:
                if (e.isLeftClick()) this.addTimeCoolDown();
                else if (e.isRightClick()) this.removeTimeCoolDown();
                break;
            case SNOW_BALL:
                if (e.isLeftClick()) this.addBallCoolDown();
                else if (e.isRightClick()) this.removeBallCoolDown();
                break;
            case ARROW:
                this.playerData.getScenario().setMenu(new MenuScenario(this.playerData));
                break;
            default:
                if(itemStack.getType().getId() == 347)
                    if (e.isLeftClick()) this.addTimeBall();
                    else if (e.isRightClick()) this.removeTimeBall();
                else if((itemStack.getType().getId() == 351)){
                    this.setCoolDown();
                    this.playerData.getScenario().setMenu(new MenuScenario(this.playerData));
                }
                break;
        }
    }

    private void addTimeCoolDown(){
        if(Parameter.getCooldownBall()+1 <= Scenario.getCoolDownBallMax()){
            Parameter.setCooldownBall(Parameter.getCooldownBall()+1);
            updateItemCooldownTime();
        }
    }

    private void removeTimeCoolDown(){
        if(Parameter.getCooldownBall()-1 >= 0){
            Parameter.setCooldownBall(Parameter.getCooldownBall()-1);
            updateItemCooldownTime();
        }
    }

    private void updateItemCooldownTime(){
        playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LORE", this.playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBall()))}, 1);
    }

    private void addBallCoolDown(){
        if(Parameter.getNbrBall()+1 <= Scenario.getBallCoolDownBallMax()){
            Parameter.setNbrBall(Parameter.getNbrBall()+1);
            this.updateItemCooldownBall();
        }
    }

    private void removeBallCoolDown(){
        if(Parameter.getNbrBall()-1 >= Scenario.getBallCoolDownBallMin()){
            Parameter.setNbrBall(Parameter.getNbrBall()-1);
            this.updateItemCooldownBall();
        }
    }

    private void updateItemCooldownBall(){
        this.playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.BALL.LORE", this.playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, "§a"+Parameter.getNbrBall()))}, 3);
    }

    private void addTimeBall(){
        if(Parameter.getTimeBall()+1 <= Scenario.getBallTimeMax()){
            Parameter.setTimeBall(Parameter.getTimeBall()+1);
            this.updateItemBallTime();
        }
    }

    private void removeTimeBall(){
        if(Parameter.getTimeBall()-1 >= Scenario.getBallTimeMin()){
            Parameter.setTimeBall(Parameter.getTimeBall()-1);
            this.updateItemBallTime();
        }
    }

    private void updateItemBallTime(){
        this.playerData.getScenario().updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LORE", this.playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBall()))}, 5);
    }

    private void setCoolDown(){
        Parameter.setCoolDownBallState(!Parameter.isCoolDownBallState());
        if(Parameter.isCoolDownBallState())  this.playerData.getScenario().updateItem(8, new Item(349).withData((byte) 3).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", this.playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", this.playerData.getLang())}));
    }

}
