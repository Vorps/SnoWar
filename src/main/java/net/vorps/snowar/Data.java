package net.vorps.snowar;

import lombok.Getter;
import lombok.Setter;
import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.databases.Database;
import net.vorps.api.lang.Lang;
import net.vorps.api.lang.LangSetting;
import net.vorps.api.utils.*;
import net.vorps.snowar.exceptions.PercentException;
import net.vorps.snowar.objects.Bonus;
import net.vorps.snowar.objects.MapParameter;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.scenario.Scenario;
import org.bukkit.ChatColor;

import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Project SnoWar Created by Vorps on 19/07/2016 at 23:08.
 */
public class Data {

    private static final @Getter ChatColor[] COLOR;
    private static final @Getter String NAME_SERVER;
    private static @Getter @Setter int nbPlayerMin;
    private static @Getter @Setter int nbPlayerMax;
    private static @Getter boolean scenario;

    static {
        COLOR       =  new ChatColor[] {ChatColor.GREEN, ChatColor.YELLOW, ChatColor.GOLD, ChatColor.RED};
        NAME_SERVER =  Paths.get(System.getProperty("user.dir")).getFileName().toString();
    }

    /**
     * Load all variable plugin
     */
    public static void loadVariable(){
        Data.getLang();
        Data.getSettings();
        Data.getLocation();
        Data.getLimite();
        Data.getMessageTitle();
        Data.getItem();
        Data.getServer();
        Data.getBonus();
        Data.getFirework();
    }

    private static void getBonus(){
        net.vorps.snowar.bonus.Bonus.clear();
        try {
            ResultSet results = Database.SNOWAR.getDatabase().getDataTmp("bonus");
            while (results.next()) {
                new Bonus(results);
            }
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
        try {
            Bonus.init();
        } catch (PercentException error){
            error.printStackTrace();
        }
    }

    private static void getSettings(){
        try {
            ResultSet results = Database.SNOWAR.getDatabase().getDataTmp("setting");
            while (results.next()) new Settings(results);
            ResultSet results_server = Database.SERVER.getDatabase().getDataTmp("setting");
            while (results_server.next()) new Settings(results_server);
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
            results = Database.SERVER.getDatabase().getDataTmp("lang_setting");
            while(results.next()) new LangSetting(results);
            results = Database.SNOWAR.getDatabase().getDataTmp("lang");
            while(results.next()) new Lang(results);
            results = Database.SERVER.getDatabase().getDataTmp("lang");
            while(results.next()) new Lang(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    private static void getServer(){
        try{
            ResultSet resultServer = Database.SNOWAR.getDatabase().getData("server", "sv_name = '"+Data.NAME_SERVER+"'");
            if(resultServer.next()){
                Data.nbPlayerMin = Database.SNOWAR.getDatabase().getInt(resultServer, 3);
                Data.nbPlayerMax = Database.SNOWAR.getDatabase().getInt(resultServer, 4);
                Data.scenario = Database.SNOWAR.getDatabase().getString(resultServer, 5) != null;
                ResultSet resultParameter = Database.SNOWAR.getDatabase().getData("parameter", "p_parameter = '"+Database.SNOWAR.getDatabase().getString(resultServer, 2)+"'");
                if(resultParameter.next()){
                    Parameter.init(resultParameter);
                    ResultSet resultMap = Database.SNOWAR.getDatabase().getData("map" , "m_name = '"+Database.SNOWAR.getDatabase().getString(resultParameter, 2)+"'");
                    if(resultMap.next()) MapParameter.init(resultMap);
                }
                if(Data.scenario){
                    ResultSet resultSet =Database.SNOWAR.getDatabase().getData("scenario", "scen_name = '"+Database.SNOWAR.getDatabase().getString(resultServer, 5)+"'");
                    if(resultSet.next())Scenario.init(resultSet);
                }
            }
        } catch(SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    private static void getItem(){
        Item.clear();
        ResultSet results;
        try {
            results = Database.SNOWAR.getDatabase().getDataTmp("item");
            while (results.next()) new Item(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    private static void getLimite(){
        Limite.clear();
        try {
            ResultSet results = Database.SNOWAR.getDatabase().getDataTmp("limite");
            while (results.next()) new Limite(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    private static void getMessageTitle(){
        MessageTitle.clear();
        try {
            ResultSet results = Database.SNOWAR.getDatabase().getDataTmp("message_title");
            while (results.next()) new MessageTitle(results);
        } catch (SQLException e){
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    private static void getFirework(){
        Firework.clear();
        try {
            ResultSet results = Database.SNOWAR.getDatabase().getDataTmp("firework");
            while (results.next()) new Firework(results);
        } catch (SQLException e){
            //
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

}
