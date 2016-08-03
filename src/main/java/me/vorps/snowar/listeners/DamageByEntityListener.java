package me.vorps.snowar.listeners;

import me.vorps.snowar.SnowWar;
import me.vorps.snowar.game.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.cooldowns.CoolDownsLastDamage;
import me.vorps.snowar.objects.Bonus;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.threads.ThreadSpawnKill;
import me.vorps.snowar.utils.ActionBar;
import me.vorps.snowar.utils.Hologram;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class DamageByEntityListener implements Listener{

    @EventHandler
    public void onDamagesByEntity(EntityDamageByEntityEvent e){
        if(GameState.isState(GameState.INGAME) && e.getDamager() instanceof Snowball && ((Snowball)e.getDamager()).getShooter() instanceof Player){
            if(e.getEntity() instanceof Player){
                String killed = e.getEntity().getName();
                String killer = ((Player) ((Snowball)e.getDamager()).getShooter()).getName();
                PlayerData playerDataKilled = PlayerData.getPlayerData(killed);
                PlayerData playerDataKiller = PlayerData.getPlayerData(killer);
                if(playerDataKilled.isGod()){
                    playerDataKilled.setGod(false);
                }
                if(!playerDataKiller.isGod()) {
                    PlayerData.getPlayerData(killed).setPlayerLastDamage(killer);
                    new CoolDownsLastDamage(PlayerData.getPlayerData(killed)).start();
                    if(playerDataKilled.getPlayer().getHealth()-Parameter.getDamage() <= 0){
                        if(playerDataKilled.getLife() > 1){
                            new ThreadSpawnKill(playerDataKilled.getPlayer());
                        }
                        playerDataKiller.addKill();
                        playerDataKilled.removeLife(killer);
                    } else {
                        playerDataKilled.getPlayer().damage(Parameter.getDamage());
                        String heart = "";
                        for(int i = 0; i <= playerDataKilled.getPlayer().getHealthScale(); i++){
                            heart += i < playerDataKilled.getPlayer().getHealth() ? "§c❤" : "§7❤";
                        }
                        ActionBar.sendActionBar(heart, playerDataKiller.getPlayer());
                    }
                    playerDataKiller.addBallTouch();
                }
            }
            if(e.getEntity() instanceof Snowman){
                e.getEntity().remove();
                Bonus.give(PlayerData.getPlayerData(((Player) ((Snowball)e.getDamager()).getShooter()).getName()));
            }
        }
        e.setCancelled(true);
    }
}