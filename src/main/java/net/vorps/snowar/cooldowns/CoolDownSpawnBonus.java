package net.vorps.snowar.cooldowns;

import net.vorps.snowar.SnoWar;
import net.vorps.snowar.objects.MapParameter;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.utils.LocationFix;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Random;

/**
 * Project SnoWar Created by Vorps on 23/07/2016 at 22:51.
 */
public class CoolDownSpawnBonus {

    /**
     * Spawn SnoMan for bonus
     */
    public CoolDownSpawnBonus(){
        run();
    }

    private void run(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SnoWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                int var = new Random().nextInt(360);
                int distance = new Random().nextInt((int) MapParameter.getDistance());
                Bukkit.getWorlds().get(0).spawnEntity(LocationFix.getLocation(new Location(Bukkit.getWorlds().get(0), Math.cos(var)*distance+MapParameter.getSpawnGame().getX(),  MapParameter.getSpawnGame().getY(), Math.sin(var)*distance+MapParameter.getSpawnGame().getZ())), EntityType.SNOWMAN);
            }
        }, 0L, Parameter.getTimeBonus()*20);
    }
}
