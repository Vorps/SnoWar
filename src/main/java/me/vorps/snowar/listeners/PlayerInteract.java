package me.vorps.snowar.listeners;

import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.cooldowns.CoolDownBonus;
import me.vorps.snowar.cooldowns.CoolDowns;
import me.vorps.snowar.utils.Item;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

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
                        playerData.removeBall();
                        if(!playerData.getBonusData().isEmpty()){
                            for(CoolDownBonus bonus : playerData.getBonusData()){
                                bonus.getBonusData().getBonus().onUse(playerData, e);
                            }
                        }
                    }
                    if(item.isSimilar(Item.getItem("gun", playerData.getLang()).get())){
                        playerData.getPlayer().launchProjectile(Snowball.class);
                        if(new Random().nextBoolean()){
                            playerData.getPlayer().launchProjectile(Snowball.class).getVelocity().multiply(1.5);
                        }
                    }
                }
            } else {
                switch (item.getType()){
                    case BED:
                        // TODO: 21/07/2016 Retour lobby implementation
                        break;
                    default:
                        break;
                }
            }
        }
	}
}
