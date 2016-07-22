package me.vorps.snowar.listeners;

import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.cooldowns.CoolDownsLastDamage;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.threads.ThreadSpawnKill;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class DamageByEntityListener implements Listener{

    @EventHandler
    public void onDamagesByEntity(EntityDamageByEntityEvent e){
        if(GameState.isState(GameState.INGAME) && e.getEntity() instanceof Snowball){
            String killed = e.getEntity().getName();
            String killer = ((Player) ((Snowball)e.getDamager()).getShooter()).getName();
            PlayerData playerDataKilled = PlayerData.getPlayerData(killer);
            PlayerData playerDataKiller = PlayerData.getPlayerData(killed);
            if(playerDataKilled.isGod()){
                playerDataKilled.setGod(false);
            }
            if(!playerDataKiller.isGod()) {
                PlayerData.getPlayerData(killed).setPlayerLastDamage(killer);
                new CoolDownsLastDamage(PlayerData.getPlayerData(killed)).start();
                if(playerDataKilled.getPlayer().getHealth()-Parameter.getDamage() == 0){
                    new ThreadSpawnKill(playerDataKilled.getPlayer());
                    playerDataKiller.addKill();
                    playerDataKilled.removeLife(killer);
                } else {
                    playerDataKilled.getPlayer().setHealth(playerDataKilled.getPlayer().getHealth()-Parameter.getDamage());
                }
                playerDataKiller.addBallTouch();
            }
        }
        e.setCancelled(true);
    }
}