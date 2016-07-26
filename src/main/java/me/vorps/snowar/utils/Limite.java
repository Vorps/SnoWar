package me.vorps.snowar.utils;

import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Limite {

    public Limite(ResultSet result) throws SqlException {
        listLimite.put(Database.SNOWAR.getDatabase().getString(result, 1), new double[] {Database.SNOWAR.getDatabase().getDouble(result, 2), Database.SNOWAR.getDatabase().getDouble(result, 3), Database.SNOWAR.getDatabase().getDouble(result, 4), Database.SNOWAR.getDatabase().getDouble(result, 5), Database.SNOWAR.getDatabase().getDouble(result, 6), Database.SNOWAR.getDatabase().getDouble(result, 7)});
    }

    private static HashMap<String, double[]> listLimite;

    static {
        listLimite = new HashMap<>();
    }

    public static double[] getLimite(String nameLimite){
        return Limite.listLimite.get(nameLimite);
    }

    public static boolean limite(Location loc, double[] limite){
        return loc.getX() > limite[0] || loc.getX() < limite[1] || loc.getY() > limite[2] || loc.getY() < limite[3] || loc.getZ() > limite[4] || loc.getZ() < limite[5];
    }

    public static void clear(){
        Limite.listLimite.clear();
    }
}
