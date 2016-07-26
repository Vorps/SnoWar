package me.vorps.snowar.utils;

import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Location {

    public Location(ResultSet result) throws SqlException {
        listLocation.put(Database.SNOWAR.getDatabase().getString(result, 1), new org.bukkit.Location(Bukkit.getWorlds().get(0), Database.SNOWAR.getDatabase().getDouble(result, 2), Database.SNOWAR.getDatabase().getDouble(result, 3), Database.SNOWAR.getDatabase().getDouble(result, 4), Database.SNOWAR.getDatabase().getFloat(result, 5), Database.SNOWAR.getDatabase().getFloat(result, 6)));
    }

    private static HashMap<String, org.bukkit.Location> listLocation;

    static {
        listLocation = new HashMap<>();
    }
    public static void clear(){
        listLocation.clear();
    }

    public static org.bukkit.Location getLocation(String name){
        return Location.listLocation.get(name);
    }
}
