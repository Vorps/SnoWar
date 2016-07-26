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

    private boolean valueBoolean;
    private int valueInt;
    private String message;

    /**
     * Add parameter settings
     * @param result ResultSet
     * @throws SqlException
     */
    public Settings(ResultSet result) throws SqlException{
        valueBoolean = Database.SNOWAR.getDatabase().getBoolean(result, 2);
        valueInt     = Database.SNOWAR.getDatabase().getInt(result, 3);
        message      = Database.SNOWAR.getDatabase().getString(result, 4);

        settings.put(Database.SNOWAR.getDatabase().getString(result, 1), this);
    }

    private static HashMap<String, Settings> settings;

    private static @Getter String  title;
    private static @Getter String  ip;
    private static @Getter String  consoleLang;
    private static @Getter String  nameVillagerStats;
    private static @Getter int     timeStart;
    private static @Getter String  tabListHeader;
    private static @Getter String  tabListFooter;
    private static @Getter int     coolDownLastDamage;
    private static @Getter int     coolDownSpawnKill;
    private static @Getter int     timeFinish;
    private static @Getter boolean showStatFinish;
    private static @Getter int     timeMessage;

    static {
        settings = new HashMap<>();
    }

    /**
     * Affected variable setting
     */
    public static void initSettings(){
        Settings.title              = Settings.settings.get("title").message;
        Settings.ip                =  Settings.settings.get("ip").message;
        Settings.consoleLang       =  Settings.settings.get("console_lang").message;
        Settings.nameVillagerStats =  Settings.settings.get("name_villager_stats").message;
        Settings.timeStart         =  Settings.settings.get("time_start").valueInt;
        Settings.tabListHeader     =  Settings.settings.get("tab_list_header").message;
        Settings.tabListFooter     =  Settings.settings.get("tab_list_footer").message;
        Settings.coolDownLastDamage = Settings.settings.get("cooldown_last_damage").valueInt;
        Settings.coolDownSpawnKill =  Settings.settings.get("cooldown_spawn_kill").valueInt;
        Settings.timeFinish        =  Settings.settings.get("time_finish").valueInt;
        Settings.showStatFinish    =  Settings.settings.get("show_stats_finish").valueBoolean;
        Settings.timeMessage       =  Settings.settings.get("time_message").valueInt;

        Settings.clear();
    }

    /**
     * Clear all setting
     */
    private static void clear(){
        Settings.settings.clear();
    }
}
