package me.vorps.snowar.threads;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.SnowWar;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class ThreadSpawnKill {

    private Player player;
    private int task;
    private int time;

    public ThreadSpawnKill(Player player){
        this.player = player;
        time = 0;
        PlayerData.getPlayerData(player.getName()).setGod(true);
        run();
    }

    private void run(){
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(time <= Settings.getCoolDownSpawnKill()*10 && PlayerData.getPlayerData(player.getName()).isGod()){
                    player.playSound(player.getLocation(), Sound.CLICK, 10F, 10F);
                } else {
                    Bukkit.getScheduler().cancelTask(task);
                    PlayerData.getPlayerData(player.getName()).setGod(false);
                }
                time++;
            }
        }, 0L, 20L);
    }
}
