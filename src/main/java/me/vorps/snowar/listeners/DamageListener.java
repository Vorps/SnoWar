package me.vorps.snowar.listeners;

import me.vorps.snowar.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener{

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(!GameState.isState(GameState.INGAME)){
                e.setCancelled(true);
            } else {
                if(e.getCause() != EntityDamageEvent.DamageCause.FALL){
                    e.setCancelled(true);
                }
            }
        }
    }
}