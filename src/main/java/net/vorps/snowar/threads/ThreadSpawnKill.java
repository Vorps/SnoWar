package net.vorps.snowar.threads;

import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.SnoWar;
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
        this.time = 0;
        PlayerData.getPlayerData(player.getName()).setGod(true);
        this.run();
    }

    private void run(){
        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnoWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(time <= Settings.getCoolDownSpawnKill()*10 && PlayerData.getPlayerData(player.getName()).isGod()) player.playSound(player.getLocation(), Sound.CLICK, 0.1F, 0.20F);
                else {
                    Bukkit.getScheduler().cancelTask(task);
                    PlayerData.getPlayerData(player.getName()).setGod(false);
                }
                time++;
            }
        }, 0L, 20L);
    }
}
