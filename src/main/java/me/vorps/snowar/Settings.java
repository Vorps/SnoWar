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

    public static void initSettings(){
        title = Settings.settings.get("title").message;
        ip = Settings.settings.get("ip").message;
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
