package me.vorps.snowar.objects;

import lombok.Getter;
import me.vorps.snowar.Settings;
import me.vorps.syluriapi.Exceptions.SqlException;
import me.vorps.syluriapi.databases.Database;
import me.vorps.syluriapi.utils.EntityManager;
import me.vorps.syluriapi.utils.Limite;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.sql.ResultSet;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class MapParameter {
    private static @Getter String name;
    private static @Getter Location spawnLobby;
    private static @Getter Location spawnFinish;
    private static @Getter Location spawnGame;
    private static Location spawnStats;
    private static @Getter double distance;
    private static @Getter double[] limit;

    /**
     * Init variable map
     * @param result ResultSet
     * @throws SqlException
     */
    public static void init(ResultSet result) throws SqlException{
        MapParameter.name = Database.SNOWAR.getDatabase().getString(result, 1);
        MapParameter.spawnLobby = me.vorps.syluriapi.utils.Location.getLocation(Database.SNOWAR.getDatabase().getString(result, 2));
        MapParameter.spawnFinish = me.vorps.syluriapi.utils.Location.getLocation(Database.SNOWAR.getDatabase().getString(result, 3));
        MapParameter.spawnStats = me.vorps.syluriapi.utils.Location.getLocation(Database.SNOWAR.getDatabase().getString(result, 4));
        MapParameter.spawnGame = me.vorps.syluriapi.utils.Location.getLocation(Database.SNOWAR.getDatabase().getString(result, 5));
        MapParameter.distance = Database.SNOWAR.getDatabase().getDouble(result, 6);
        MapParameter.limit = Limite.getLimite(Database.SNOWAR.getDatabase().getString(result, 7));
    }

    /**
     * Spawn statistic Villager
     */
    public static void spawnStats(){
        EntityManager.entityManager(EntityType.VILLAGER, spawnStats, Settings.getNameVillagerStats(), Villager.Profession.BUTCHER);
    }
}
