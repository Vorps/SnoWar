package me.vorps.snowar.objects;

import lombok.Getter;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.Settings;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.utils.EntityManager;
import me.vorps.snowar.utils.Limite;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.sql.ResultSet;

/**
 * Project RushVolcano Created by Vorps on 21/04/2016 at 22:56.
 */
public class MapParameter {
    private static @Getter String name;
    private static @Getter Location spawnLobby;
    private static @Getter Location spawnFinish;
    private static Location spawnStats;
    private static @Getter double[] limit;

    public static void init(ResultSet result) throws SqlException{
        name = Database.SNOWAR.getDatabase().getString(result, 1);
        spawnLobby = me.vorps.snowar.utils.Location.getLocation(Database.SNOWAR.getDatabase().getString(result, 2));
        spawnFinish = me.vorps.snowar.utils.Location.getLocation(Database.SNOWAR.getDatabase().getString(result, 3));
        spawnStats = me.vorps.snowar.utils.Location.getLocation(Database.SNOWAR.getDatabase().getString(result, 4));
        limit = Limite.getLimite(Database.SNOWAR.getDatabase().getString(result, 5));
    }

    public static void spawnStats(){
        EntityManager.entityManager(EntityType.VILLAGER, spawnStats, Settings.getNameVillagerStats(), Villager.Profession.BUTCHER);
    }
}
