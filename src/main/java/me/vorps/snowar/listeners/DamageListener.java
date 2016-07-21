package me.vorps.snowar.listeners;

import me.vorps.snowar.Data;
import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class DamageListener implements Listener{

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(!GameState.isState(GameState.INGAME)){
                e.setCancelled(true);
            } else {
                if(e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                    if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
                        if(Data.isFall()){
                           e.setCancelled(true);
                        }
                    }
                    if(!e.isCancelled() && ((Player) e.getEntity()).getHealth()-e.getDamage() <= 0){
                        PlayerData playerData = PlayerData.getPlayerData(e.getEntity().getName());
                        if(playerData.getPlayerLastDamage() != null){
                            PlayerData playerDataKiller = PlayerData.getPlayerData(playerData.getPlayerLastDamage());
                            playerDataKiller.addKill();
                        }
                        playerData.removeLife(playerData.getPlayerLastDamage() == null ? e.getCause().name() : playerData.getPlayerLastDamage());
                    }
                }
            }
        }
    }
}