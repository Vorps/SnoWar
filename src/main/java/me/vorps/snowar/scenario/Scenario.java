package me.vorps.snowar.scenario;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.Data;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.lang.LangSetting;
import me.vorps.snowar.menu.Item;
import me.vorps.snowar.menu.Menu;
import me.vorps.snowar.menu.MenuBonus;
import me.vorps.snowar.menu.MenuScenario;
import me.vorps.snowar.objects.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Project RushVolcano Created by Vorps on 27/04/2016 at 16:41.
 */
public class Scenario {

    public enum Hour {
        MORNING(23000, "morning"),
        DAY(0, "day"),
        EVENING(12000, "evening"),
        NIGHT(13500, "night");

        private long time;
        private @Getter HashMap<String, String> label;

        Hour(long time, String label){
            this.label = new HashMap<>();
            this.time = time;
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
                this.label.put(langSetting.getName(), "§a"+Lang.getMessage("SNO_WAR.SCENARIO."+label.toUpperCase(), langSetting.getName()));
            }
        }

        public static Hour getHour(long time){
            Hour hour = DAY;
            for(Hour hourTmp : Hour.values()){
                Scenario.hour++;
                if(hourTmp.time == time){
                    hour = hourTmp;
                    break;
                }
            }
            return hour;
        }
    }

    public static void init(ResultSet resultSet) throws SqlException{
        timeMin = Database.SNOWAR.getDatabase().getInt(resultSet, 2);
        timeMax = Database.SNOWAR.getDatabase().getInt(resultSet, 3);
        nbPlayerMin = Database.SNOWAR.getDatabase().getInt(resultSet, 4);
        nbPlayerMax = Database.SNOWAR.getDatabase().getInt(resultSet, 5);
        speedBonusMin = Database.SNOWAR.getDatabase().getInt(resultSet, 6);
        speedBonusMax = Database.SNOWAR.getDatabase().getInt(resultSet, 7);
        damageMin = Database.SNOWAR.getDatabase().getDouble(resultSet, 8);
        damageMax = Database.SNOWAR.getDatabase().getDouble(resultSet, 9);
    }

    private static int timeMin;
    private static int timeMax;
    private static int nbPlayerMin;
    private static int nbPlayerMax;
    private static int hour;
    private static int speedBonusMin;
    private static int speedBonusMax;
    private static double damageMin;
    private static double damageMax;

    private static @Setter Menu menu;

    public static void addSpeedBonus(){
        if(Parameter.getTimeBonus()+1 <= Scenario.speedBonusMax){
            Parameter.setTimeBonus(Parameter.getTimeBonus()+1);
            updateItemSpeedBonus();
        }
    }

    public static void addDamage(){
        if(Parameter.getDamage()+0.5 <= Scenario.damageMax){
            Parameter.setDamage(Parameter.getDamage()+0.5);
            Scenario.updateItemDamage();
        }
    }

    public static void removeDamage(){
        if(Parameter.getDamage()-0.5 >= Scenario.damageMin){
            Parameter.setDamage(Parameter.getDamage()-0.5);
            Scenario.updateItemDamage();
        }
    }

    private static void updateItemDamage(){
        ((MenuScenario) menu).updateItem(new String[] {"§a"+Parameter.getDamage()}, 6);
    }

    public static void setBonus(){
        Parameter.setBonus(!Parameter.isBonus());
        if(Parameter.isBonus())  ((MenuScenario) menu).updateItem(5, new Item(Material.SNOW_BLOCK).withName("§6Bonus").withLore(new String[] {"§7Configurer les bonus du jeu"}));
        else ((MenuScenario) menu).updateItem(5, new Item(351).withData((byte) 8).withName("§6Bonus").withLore(new String[] {"§cDésactivé"}));
    }
    public static void removeSpeedBonus(){
        if(Parameter.getTimeBonus()-1 >= Scenario.speedBonusMin){
            Parameter.setTimeBonus(Parameter.getTimeBonus()-1);
            updateItemSpeedBonus();
        }
    }

    private static void updateItemSpeedBonus(){
        ((MenuBonus)menu).updateItem(new String[] {"§a"+Parameter.getTimeBonus()}, ((MenuBonus)menu).getSize()-6);
    }

    public static void addTime(){
        if(Parameter.getTimeGame()+60 <= Scenario.timeMax){
            Parameter.setTimeGame(Parameter.getTimeGame()+60);
            updateItemTime();
        }
    }

    public static void removeTime(){
        if(Parameter.getTimeGame()-60 >= Scenario.timeMin){
            Parameter.setTimeGame(Parameter.getTimeGame()-60);
            updateItemTime();
        }
    }

    public static void addHour(String lang){
        if(++hour >= 4) hour = 0;
        Hour hourVar = Hour.values()[hour];
        Bukkit.getWorlds().get(0).setTime(hourVar.time);
        ((MenuScenario) menu).updateItem(new String[] {hourVar.label.get(lang)}, 1);
    }

    public static void addNbrPlayer(){
        if(Data.getNbPlayerMax()+1 <= Scenario.nbPlayerMax){
            Data.setNbPlayerMax(Data.getNbPlayerMax()+1);
            updateItemNbrPlayer();
        }
    }

    public static void removeNbrPlayer(){
        if(Data.getNbPlayerMax()-1 >= Scenario.nbPlayerMin){
            Data.setNbPlayerMax(Data.getNbPlayerMax()-1);
            updateItemNbrPlayer();
        }
    }

    public static void setFall(){
        Parameter.setFall(!Parameter.isFall());
        if(Parameter.isFall()) ((MenuScenario) menu).updateItem((byte) 10,new String[] {"§aActivé"}, 3);
        else ((MenuScenario) menu).updateItem((byte) 8,new String[] {"§cDésactivé"}, 3);

    }

    public static void setCycle(){
        Parameter.setCycle(!Parameter.isCycle());
        Bukkit.getWorlds().get(0).setGameRuleValue("doDaylightCycle", ""+Parameter.isCycle());
        if(Parameter.isCycle()) ((MenuScenario) menu).updateItem((byte) 10,new String[] {"§aActivé"}, 4);
        else ((MenuScenario) menu).updateItem((byte) 8,new String[] {"§cDésactivé"}, 4);
    }


    private static void updateItemTime(){
        Date date = new Date(Parameter.getTimeGame()*1000);
        date.setHours(date.getHours()-1);
        ((MenuScenario) menu).updateItem(new String[] {"§6Temps : §a"+new SimpleDateFormat("HH:mm:ss").format(date)}, 0);
    }

    private static void updateItemNbrPlayer(){
        ((MenuScenario) menu).updateItem(new String[] {"§a"+Data.getNbPlayerMax()}, 2);
    }
}
