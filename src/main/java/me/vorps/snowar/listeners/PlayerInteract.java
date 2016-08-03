package me.vorps.snowar.listeners;

import me.vorps.snowar.SnowWar;
import me.vorps.snowar.game.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.bonus.Bonus;
import me.vorps.snowar.cooldowns.CoolDowns;
import me.vorps.snowar.menu.MenuScenario;
import me.vorps.snowar.scenario.Scenario;
import me.vorps.snowar.utils.Item;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class PlayerInteract implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		ItemStack item = e.getItem();
		Action action = e.getAction();
        PlayerData playerData = PlayerData.getPlayerData(e.getPlayer().getName());
        if(action != Action.PHYSICAL && item != null && item.getType() != Material.AIR){
            if (GameState.isState(GameState.INGAME)) {
                if (action == Action.RIGHT_CLICK_BLOCK && item.getType().getId() == 256 && e.getClickedBlock().getType() == Material.SNOW) {
                    if(CoolDowns.hasCoolDown(e.getPlayer().getName(), "ball") && CoolDowns.getCoolDown(e.getPlayer().getName(), "ball").getSecondsLeft() <= 0){
                        CoolDowns.getCoolDown(e.getPlayer().getName(), "ball").removeCoolDown();
                    }
                    if(playerData.getBall().size() < 16 && !CoolDowns.hasCoolDown(e.getPlayer().getName(), "ball")){
                        Block block = e.getClickedBlock();
                        if(block.getData() > 0){
                            block.setData((byte) (block.getData() - 1));
                        } else {
                            block.setType(Material.AIR);
                        }
                        playerData.getPlayer().getInventory().addItem(Item.getItem("ball", playerData.getLang()).get());
                        playerData.addBall();
                    }
                }
                if((action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR)){
                    if(item.getType() == Material.SNOW_BALL){
                        boolean state = true;
                        for(Bonus bonus : playerData.getBonusData().keySet()){
                            state = bonus.getItemStack() == null ? true : item.isSimilar(bonus.getItemStack()) ? true : false;
                        }
                        if(state){
                            playerData.removeBall();
                        }
                    }
                    playerData.getBonusData().keySet().forEach((Bonus bonus)  -> {
                        if(bonus.getItemStack() != null && item.isSimilar(bonus.getItemStack())){
                            bonus.onUse(playerData, e);
                        }
                    });
                }
            } else {
                switch (item.getType()){
                    case BED:
                        // TODO: 21/07/2016 Retour lobby implementation
                        break;
                    case REDSTONE_COMPARATOR:
                        Scenario.setMenu(new MenuScenario(playerData));
                        break;
                    default:
                        break;
                }
            }
        }
	}
}
