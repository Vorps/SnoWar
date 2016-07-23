package me.vorps.snowar.cooldowns;

import me.vorps.snowar.SnowWar;
import org.bukkit.Bukkit;

/**
 * Project SnoWar Created by Vorps on 23/07/2016 at 22:51.
 */
public class CoolDownBonus {

    public CoolDownBonus(){

    }

    public void run(){
        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {

            }
        }, 0L, 20L);
    }
}
