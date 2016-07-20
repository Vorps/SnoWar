package me.vorps.snowar.utils;

import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project FortycubeBungee Created by Vorps on 30/03/2016 at 22:34.
 */
public class LangSetting {

    private static HashMap<String, LangSetting> listLangSetting = new HashMap<>();

    private String name;
    private String columnId;
    private String nameDisplay;

    public LangSetting(ResultSet result) throws SqlException {
        name = Database.SNOWAR.getDatabase().getString(result, 1);
        columnId = Database.SNOWAR.getDatabase().getString(result, 2);
        nameDisplay = Database.SNOWAR.getDatabase().getString(result, 3);
        listLangSetting.put(name, this);
    }

    public static HashMap<String, LangSetting> getListLangSetting(){
        return listLangSetting;
    }

    public String getName(){
        return name;
    }

    public String getColumnId(){
        return columnId;
    }

    public String getNameDisplay(){
        return nameDisplay;
    }

    public static void clearLangSetting(){
        listLangSetting.clear();
    }
}
