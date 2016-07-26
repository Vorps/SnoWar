package me.vorps.snowar.utils;

import me.vorps.snowar.SnowWar;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Project RushVolcano Created by Vorps on 23/06/2016 at 05:14.
 */
public class Firework {

    private int task;
    private int time;

    public Firework(Player player, org.bukkit.Location location, Color mainColor, Color fadeColor, FireworkEffect.Type type, int speed, final int timeExec){
        this.time = timeExec;
        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnowWar.getInstance(), new Runnable() {
            public void run() {
                org.bukkit.entity.Firework f = player.getWorld().spawn(location, org.bukkit.entity.Firework.class);
                FireworkMeta fm = f.getFireworkMeta();
                fm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(type).withColor(mainColor).withFade(fadeColor).build());
                fm.setPower(3);
                f.setFireworkMeta(fm);
                if(time == 0){
                    Bukkit.getScheduler().cancelTask(task);
                }
                time -= speed/20;
            }
        },0L, speed);
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(task);
    }
}
