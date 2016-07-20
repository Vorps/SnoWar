package me.vorps.snowar;

import lombok.Getter;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.LangSetting;
import me.vorps.snowar.utils.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Project SnoWar Created by Vorps on 19/07/2016 at 23:08.
 */
public class Data {

    private static @Getter int numberPlayer;

    public static void loadVariable(){
        getLang();
        getSettings();
        getLocation();
    }

    private static void getSettings(){
        try {
            ResultSet results = Database.SNOWAR.getDatabase().getDataTmp("setting");
            while (results.next()) {
                new Settings(results);
            }
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
        Settings.initSettings();
    }

    private static void getLocation(){
        Location.clear();
        ResultSet results;
        try {
            results = Database.SNOWAR.getDatabase().getDataTmp("location");
            while (results.next()) {
                new Location(results);
            }
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static void getLang(){
        Lang.clearLang();
        LangSetting.clearLangSetting();
        ResultSet results;
        try {
            results = Database.SNOWAR.getDatabase().getDataTmp("lang_setting");
            while(results.next()) {
                new LangSetting(results);
            }
            results = Database.SNOWAR.getDatabase().getDataTmp("lang");
            while(results.next()) {
                new Lang(results);
            }
            results = Database.SNOWAR.getDatabase().getDataTmp("lang");
            while(results.next()) {
                new Lang(results);
            }
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

}
