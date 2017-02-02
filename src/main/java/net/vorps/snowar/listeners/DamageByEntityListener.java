package net.vorps.snowar.listeners;

import net.vorps.api.type.GameState;
import net.vorps.api.utils.ActionBar;
import net.vorps.api.utils.Hologram;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.cooldowns.CoolDownsLastDamage;
import net.vorps.snowar.objects.Bonus;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.threads.ThreadSpawnKill;
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
                if(playerDataKilled.isGod()) playerDataKilled.setGod(false);
                if(!playerDataKiller.isGod()) {
                    PlayerData.getPlayerData(killed).setPlayerLastDamage(killer);
                    new CoolDownsLastDamage(PlayerData.getPlayerData(killed)).start();
                    if(playerDataKilled.getPlayer().getHealth()-Parameter.getDamage() <= 0){
                        if(playerDataKilled.getLife() > 1) new ThreadSpawnKill(playerDataKilled.getPlayer());
                        new Hologram(0.3, new Hologram.Message("1", "§cKill"), new Hologram.Message("2", "§6"+playerDataKiller.getPlayer().getName())).show(playerDataKilled.getPlayer().getLocation(), 0.7, 2000, SnoWar.getInstance());
                        playerDataKiller.addKill();
                        playerDataKilled.removeLife(killer);
                    } else {
                        playerDataKilled.getPlayer().damage(Parameter.getDamage());
                        if(playerDataKiller.isEffect()){
                            String heart = "";
                            for(int i = 1; i <= playerDataKilled.getPlayer().getHealthScale()/2; i++) heart += i < playerDataKilled.getPlayer().getHealth()/2 ? "§c❤" : "§7❤";
                            ActionBar.sendActionBar(heart, playerDataKiller.getPlayer());
                        }
                    }
                    playerDataKiller.addBallTouch();
                }
            }
            if(e.getEntity() instanceof Snowman){
                e.getEntity().remove();
                new Hologram(0.3, new Hologram.Message("1", "§6Bonus"), new Hologram.Message("2", Bonus.give(PlayerData.getPlayerData(((Player) ((Snowball)e.getDamager()).getShooter()).getName())).toString())).show(e.getEntity().getLocation(), 0, 2000, SnoWar.getInstance());
            }
        }
        e.setCancelled(true);
    }
}