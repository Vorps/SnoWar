package me.vorps.snowar;

import lombok.Getter;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 19/07/2016 at 23:01.
 */
public class Settings {
    private static HashMap<String, Settings> settings = new HashMap<>();
    private static @Getter String title;
    private static @Getter String ip;
    private static @Getter String consoleLang;
    private static @Getter String nameVillagerStats;
    private static @Getter int timeStart;
    private static @Getter String tabListHeader;
    private static @Getter String tabListFooter;
    private static @Getter int coolDownLastDamage;
    private static @Getter int coolDownSpawnKill;
    private static @Getter int timeFinish;
    private static @Getter boolean showStatFinish;

    public static void initSettings(){
        title = Settings.settings.get("title").message;
        ip = Settings.settings.get("ip").message;
        consoleLang = Settings.settings.get("console_lang").message;
        nameVillagerStats = Settings.settings.get("name_villager_stats").message;
        timeStart = Settings.settings.get("time_start").valueInt;
        tabListHeader = Settings.settings.get("tab_list_header").message;
        tabListFooter = Settings.settings.get("tab_list_footer").message;
        coolDownLastDamage = Settings.settings.get("cooldown_last_damage").valueInt;
        coolDownSpawnKill = Settings.settings.get("cooldown_spawn_kill").valueInt;
        timeFinish = Settings.settings.get("time_finish").valueInt;
        showStatFinish =  Settings.settings.get("show_stats_finish").valueBoolean;
        settings.clear();
    }

    private boolean valueBoolean;
    private int valueInt;
    private String message;

    public Settings(ResultSet result) throws SqlException{
        valueBoolean = Database.SNOWAR.getDatabase().getBoolean(result, 2);
        valueInt = Database.SNOWAR.getDatabase().getInt(result, 3);
        message = Database.SNOWAR.getDatabase().getString(result, 4);
        settings.put(Database.SNOWAR.getDatabase().getString(result, 1), this);
    }
}