package me.vorps.snowar.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Project SnoWar Created by Vorps on 29/08/2016 at 12:00.
 */
public class LocationFix {

    public static Location getLocation(Location location){
        for(int y = location.getBlockY(); y < Bukkit.getWorlds().get(0).getMaxHeight(); y++){
            if(location.getBlock().getType() != Material.AIR){
                location.add(0, 1, 0);
            } else {
                break;
            }
        }
        return location;
    }
}
